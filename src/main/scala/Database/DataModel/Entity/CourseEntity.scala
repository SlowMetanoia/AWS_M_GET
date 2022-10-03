package Database.DataModel.Entity

import Database.DataModel.EntityModel
import Database.DataModel.Table.CQCElementTable.cqc
import Database.DataModel.Table.CourseInputLeafsTable.{cil, cilC}
import Database.DataModel.Table.CourseOutputLeafsTable.{col, colC}
import Database.DataModel.Table.CourseTable.{c, cC}
import Database.DataModel.Table.{CQCElementTable, CourseInputLeafsTable, CourseOutputLeafsTable, CourseTable}
import Database.Mapper.{CQCElementMapper, CourseMapper}
import Database.Signature.Entity.CourseEntitySignature
import scalikejdbc._

import java.util.UUID

case class CourseEntity(id: UUID,
                        name: String,
                        inputLeaf: Seq[CQCElementEntity],
                        outputLeaf: Seq[CQCElementEntity]
                       ) extends CourseEntitySignature with EntityModel {

  override def parts(implicit session: DBSession): Map[String, Seq[CQCElementEntity]] = {
    val cqcElements: Seq[CQCElementTable] =
      sql"""
          SELECT * FROM get_course_parts(array[${outputLeaf.map(_.id)}])
       """.map(elem => CQCElementTable(
        id = UUID.fromString(elem.string("id")),
        parentId = UUID.fromString(elem.string("parent_id")),
        elemType = elem.string("type"),
        value = elem.string("value")
      )).collection.apply()

    cqcElements.map(CQCElementMapper.tableRow2Entity).groupBy(_.elemType)
  }
}

object CourseEntity extends CourseDAO with UUIDFactory {
  /**
   * Вставка связей входных Листов и курса
   * table: course_input_leaf_link
   *
   * @param course курс с которым необходимо создать связи
   */
  override def insertInputLeafs(course: CourseEntity)
                               (implicit session: DBSession): Unit = {
    val batchParams: Seq[Seq[Any]] = course.inputLeaf.map(leaf => Seq(course.id, leaf.id))

    withSQL {
      insertInto(CourseInputLeafsTable)
        .namedValues(
          cilC.courseId -> sqls.?,
          cilC.leafId -> sqls.?
        )
    }.batch(batchParams: _*).apply()
  }

  /**
   * Выборка входных Листов курса
   * table: course_input_leaf_link
   *
   * @param course курс, к которому ищутся листы
   * @return входные листы
   */
  override def findInputLeafs(course: CourseTable)
                             (implicit session: DBSession): Seq[CQCElementEntity] = {
    val cqcElements: Seq[CQCElementTable] =
      withSQL {
        selectFrom(CQCElementTable as cqc)
          .leftJoin(CourseInputLeafsTable as cil)
          .on(cqc.id, cil.leafId)
          .where.eq(cqc.id, course.id)
      }.map(CQCElementTable(cqc.resultName)).collection.apply()

    cqcElements.map(CQCElementMapper.tableRow2Entity)
  }

  /**
   * Удаление связи курса и вхродных листов
   *
   * @param course курс связи которого надо удалить
   */
  override def deleteInputLeafs(course: CourseEntity)
                               (implicit session: DBSession): Unit =
    withSQL {
      deleteFrom(CourseInputLeafsTable)
        .where.eq(cilC.courseId, course.id)
    }.update.apply()

  /**
   * Вставка связей выходных Листов и курса
   * table: course_output_leaf_link
   *
   * @param course курс с которым необходим создать связи
   */
  override def insertOutputLeafs(course: CourseEntity)
                                (implicit session: DBSession): Unit = {
    val batchParams: Seq[Seq[Any]] = course.outputLeaf.map(leaf => Seq(course.id, leaf.id))

    withSQL {
      insertInto(CourseOutputLeafsTable)
        .namedValues(
          colC.courseId -> sqls.?,
          colC.leafId -> sqls.?
        )
    }.batch(batchParams: _*).apply()
  }

  /**
   * Нахождение выходных Листов курса
   *
   * @param course курс, к которому ищутся листы
   * @return выходные листы
   */
  override def findOutputLeafs(course: CourseTable)
                              (implicit session: DBSession): Seq[CQCElementEntity] = {
    val cqcElements: Seq[CQCElementTable] =
      withSQL {
        selectFrom(CQCElementTable as cqc)
          .leftJoin(CourseOutputLeafsTable as col)
          .on(cqc.id, col.leafId)
          .where.eq(cqc.id, course.id)
      }.map(CQCElementTable(cqc.resultName)).collection.apply()

    cqcElements.map(CQCElementMapper.tableRow2Entity)
  }

  /**
   * Удаление связей курса и выходных Листов
   *
   * @param course курс связи с которым необходимо удалить
   */
  override def deleteOutputLeafs(course: CourseEntity)
                                (implicit session: DBSession): Unit =
    withSQL {
      deleteFrom(CourseOutputLeafsTable)
        .where.eq(colC.courseId, course.id)
    }.update.apply()


  /**
   * Вставка связей курса с входными и выходными листами
   *
   * @param course курс связи с которым необходимо вставить
   */
  override def insertLeafs(course: CourseEntity)
                          (implicit session: DBSession): Unit = {
    insertInputLeafs(course)
    insertOutputLeafs(course)
  }

  /**
   * Удаление связей курса с входными и выходными листами
   *
   * @param course курс связи с которым необходимо удалить
   */
  override def deleteLeafs(course: CourseEntity)
                          (implicit session: DBSession): Unit = {
    deleteInputLeafs(course)
    deleteOutputLeafs(course)
  }

  /**
   * Получение Entity из таблицы по id
   *
   * @param id Entity которую необходимо получить
   * @return Optional с Entity если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID)
                       (implicit session: DBSession): Option[CourseEntity] = {
    val courseRowOpt: Option[CourseTable] =
      withSQL {
        selectFrom(CourseTable as c)
          .where.eq(c.id, id)
      }.map(CourseTable(c.resultName)).single.apply()

    courseRowOpt.map(row => CourseMapper.tableRow2Entity(
      row,
      findInputLeafs(row),
      findOutputLeafs(row)))
  }

  /**
   * Удаление Entity из таблицы по id
   *
   * @param id Entity которую необходимо удалить
   */
  override def deleteById(id: UUID)
                         (implicit session: DBSession): Unit =
    withSQL {
      deleteFrom(CourseTable)
        .where.eq(c.id, id)
    }.update.apply()

  /**
   * Получение всех Entity из таблицы
   *
   * @param limit   кол-во записей которые необходимо получить
   * @param offset  отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort    порядок сортировки
   * @return последовательность всех Entity из таблицы
   */
  override def findAll(limit: Int,
                       offset: Int,
                       orderBy: SQLSyntax,
                       sort: SQLSyntax)
                      (implicit session: DBSession): Seq[CourseEntity] = {
    val courseRows: Seq[CourseTable] =
      withSQL {
        select.all(c).from(CourseTable as c)
          .orderBy(orderBy).append(sort)
          .limit(limit)
          .offset(offset)
      }.map(CourseTable(c.resultName)).collection.apply()

    courseRows.map(row => CourseMapper.tableRow2Entity(
      row,
      findInputLeafs(row),
      findOutputLeafs(row)))
  }

  /**
   * Вставка новой Entity в таблицу
   *
   * @param entity Entity которую необходимо вставить в таблицу
   */
  override def insert(entity: CourseEntity)
                     (implicit session: DBSession): Unit = {
    withSQL {
      insertInto(CourseTable)
        .namedValues(
          cC.id -> entity.id,
          cC.name -> entity.name
        )
    }.update.apply()

    insertLeafs(entity)
  }

  /**
   * Вставка сразу нескольких Entity в БД
   *
   * @param entityList список Entity которые мы хотим вставить
   */
  override def insertMultiRows(entityList: Seq[CourseEntity])
                              (implicit session: DBSession): Unit = {
    val batchCourses: Seq[Seq[Any]] = entityList.map(course => Seq(course.id, course.name))

    withSQL {
      insertInto(CourseTable)
        .namedValues(
          cC.id -> sqls.?,
          cC.name -> sqls.?
        )
    }.batch(batchCourses: _*).apply()
    entityList.foreach(insertLeafs)
  }

  /**
   * Обновление Entity в таблице
   *
   * @param entity Entity которое будет обновлено
   */
  override def update(entity: CourseEntity)
                     (implicit session: DBSession): Unit = {
    withSQL {
      QueryDSL.update(CourseTable)
        .set(
          cC.name -> entity.name
        ).where.eq(cC.id, entity.id)
    }.update.apply()

    deleteLeafs(entity)
    insertLeafs(entity)
  }
}
