package Database.Model.Entity

import Database.Mapper.CQCElementHierarchyMapper
import Database.Table.CQCElementHierarchyTable
import Database.Table.CQCElementHierarchyTable.{cqcHier, cqcHierC}
import scalikejdbc._

case class CQCElementHierarchyEntity(childType: String,
                                     parentType: String) extends CQCElementHierarchyEntitySignature

object CQCElementHierarchyEntity extends CQCElementHierarchyDAO {
  /**
   * Получение всех Уровней иерархии из таблицы
   *
   * @param limit   кол-во записей которые необходимо получить
   * @param offset  отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort    порядок сортировки
   * @return последовательность всех ровней иерархии из таблицы
   */
  override def findAll(limit: Int,
                       offset: Int,
                       orderBy: SQLSyntax,
                       sort: SQLSyntax)
                      (implicit session: DBSession): Seq[CQCElementHierarchyEntity] = {
    val rows: Seq[CQCElementHierarchyTable] =
      withSQL {
        select.all(cqcHier).from(CQCElementHierarchyTable as cqcHier)
          .orderBy(orderBy)
          .limit(limit)
          .offset(offset)
      }.map(CQCElementHierarchyTable(cqcHier.resultName)).collection.apply()

    rows.map(CQCElementHierarchyMapper.tableRow2Entity)
  }

  /**
   * Получение Уровня иерархии из таблицы по id
   *
   * @param id Уровень иерархии которую необходимо получить
   * @return Optional с Уровнем иерархии
   */
  override def findById(id: String)
                       (implicit session: DBSession): Option[CQCElementHierarchyEntity] = {
    val row: Option[CQCElementHierarchyTable] =
      withSQL {
        select.from(CQCElementHierarchyTable as cqcHier)
          .where.eq(cqcHier.childType, id)
      }.map(CQCElementHierarchyTable(cqcHier.resultName)).single.apply()

    row.map(CQCElementHierarchyMapper.tableRow2Entity)
  }

  override def findByChildType(id: String)
                              (implicit session: DBSession): Option[CQCElementHierarchyEntity] =
    findById(id)

  override def findByParentType(id: String)
                               (implicit session: DBSession): Option[CQCElementHierarchyEntity] = {
    val row: Option[CQCElementHierarchyTable] =
      withSQL {
        select.from(CQCElementHierarchyTable as cqcHier)
          .where.eq(cqcHier.parentType, id)
      }.map(CQCElementHierarchyTable(cqcHier.resultName)).single.apply()

    row.map(CQCElementHierarchyMapper.tableRow2Entity)
  }

  override def findByDoubleKey(doubleKey: (String, String))
                              (implicit session: DBSession): Option[CQCElementHierarchyEntity] = {
    val row: Option[CQCElementHierarchyTable] =
      withSQL {
        select.from(CQCElementHierarchyTable as cqcHier)
          .where.eq(cqcHier.childType, doubleKey._1)
          .and
          .eq(cqcHierC.parentType, doubleKey._2)
      }.map(CQCElementHierarchyTable(cqcHier.resultName)).single.apply()

    row.map(CQCElementHierarchyMapper.tableRow2Entity)
  }

  /**
   * Вставка нового Уровня иерархии в таблицу
   *
   * @param entity Уровень иерархии который необходимо вставить в таблицу
   */
  override def insert(entity: CQCElementHierarchyEntity)
                     (implicit session: DBSession): Unit = {
    val rows = CQCElementHierarchyMapper.entity2TableRow(entity)
    withSQL {
      insertInto(CQCElementHierarchyTable)
        .namedValues(
          cqcHierC.childType -> rows.childType,
          cqcHierC.parentType -> rows.parentType,
        )
    }.update.apply()
  }

  /**
   * Вставка сразу нескольких Уровней иерархии в БД
   *
   * @param entityList список уровней иерархии которые мы хотим вставить
   */
  override def insertMultiRows(entityList: Seq[CQCElementHierarchyEntity])
                              (implicit session: DBSession): Unit = {
    val batchParams: Seq[Seq[Any]] = entityList.map(CQCElementHierarchyMapper.entity2TableRow)
      .map(elem => Seq(elem.childType, elem.parentType))

    withSQL {
      insertInto(CQCElementHierarchyTable)
        .namedValues(
          cqcHierC.childType -> sqls.?,
          cqcHierC.parentType -> sqls.?,
        )
    }.batch(batchParams: _*).apply()
  }

  /**
   * Удаление уравно иерархии из таблицы по id
   *
   * @param id уровень иерархии который необходимо удалить
   */
  override def deleteById(id: String)
                         (implicit session: DBSession): Unit =
    withSQL {
      deleteFrom(CQCElementHierarchyTable)
        .where.eq(cqcHierC.childType, id)
    }

  override def deleteByDoubleKey(doubleKey: (String, String))
                                (implicit session: DBSession): Unit = {
    val row = findByDoubleKey(doubleKey)
    row.foreach(el => deleteById(el.childType))
  }

  /**
   * Обновление Уровня иерархии в таблице
   *
   * @param entity Уровень иерархии который будет обновлен
   */
  override def update(entity: CQCElementHierarchyEntity)
                     (implicit session: DBSession): Unit = {
    val row = CQCElementHierarchyMapper.entity2TableRow(entity)
    withSQL {
      QueryDSL.update(CQCElementHierarchyTable)
        .set(
          cqcHierC.childType -> row.childType,
          cqcHierC.parentType -> row.parentType,
        )
    }.update.apply()
  }

  override def updateByDoubleKey(doubleKey: (String, String))
                                (implicit session: DBSession): Unit = {
    val row = findByDoubleKey(doubleKey)
    row.foreach(update)
  }
}
