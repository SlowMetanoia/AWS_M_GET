package Database.Model.Entity

import Database.Model.Table.CourseTable
import Database.{ASC, Id}
import scalikejdbc.DBSession
import scalikejdbc.interpolation.SQLSyntax

import java.util.UUID

sealed trait DAO[EntityType] extends UUIDFactory {

  /**
   * Получение всех Entity из таблицы
   *
   * @param limit   кол-во записей которые необходимо получить
   * @param offset  отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort    порядок сортировки
   * @return последовательность всех Entity из таблицы
   */
  def findAll(limit: Int = 100,
              offset: Int = 0,
              orderBy: SQLSyntax = Id.value,
              sort: SQLSyntax = ASC.value)
             (implicit session: DBSession): Seq[EntityType]

  /**
   * Вставка новой Entity в таблицу
   *
   * @param entity Entity которую необходимо вставить в таблицу
   */
  def insert(entity: EntityType)
            (implicit session: DBSession): Unit

  /**
   * Вставка сразу нескольких Entity в БД
   *
   * @param entityList список Entity которые мы хотим вставить
   */
  def insertMultiRows(entityList: Seq[EntityType])
                     (implicit session: DBSession): Unit

  /**
   * Обновление Entity в таблице
   *
   * @param entity Entity которое будет обновлено
   */
  def update(entity: EntityType)
            (implicit session: DBSession): Unit

}

trait SinglePKDAO[EntityType, IdType] extends DAO[EntityType] {
  /**
   * Получение Entity из таблицы по id
   *
   * @param id Entity которую необходимо получить
   * @return Optional с Entity если такая есть в БД, иначе Option.empty
   */
  def findById(id: IdType)
              (implicit session: DBSession): Option[EntityType]

  /**
   * Удаление Entity из таблицы по id
   *
   * @param id Entity которую необходимо удалить
   */
  def deleteById(id: IdType)
                (implicit session: DBSession): Unit

}

trait DoublePKDao[EntityType, IdType] extends DAO[EntityType] {
  /**
   * Получение Entity из таблицы по id
   *
   * @param id Entity которую необходимо получить
   * @return Optional с Entity если такая есть в БД, иначе Option.empty
   */
  def findByDoubleId(id: (IdType, IdType))
                    (implicit session: DBSession): Option[EntityType]

  /**
   * Удаление Entity из таблицы по id
   *
   * @param id Entity которую необходимо удалить
   */
  def deleteByDoubleId(id: (IdType, IdType))
                      (implicit session: DBSession): Unit
}

/**
 * DAO для Элементов ККХ
 * table: cqc_element
 */
trait CQCElementDAO extends SinglePKDAO[CQCElementEntity, UUID]

/**
 * DAO для словаря Элементов ККХ
 * table: cqc_elem_dict
 */
trait CQCDictionaryDAO extends SinglePKDAO[CQCDictionaryEntity, String]

/**
 * DAO для иерархии элементов ККХ
 * table: cqc_elem_hierarchy
 */
trait CQCHierarchyDAO extends DoublePKDao[CQCHierarchyEntity, String]

/**
 * DAO для курсов
 * table: course
 */
trait CourseDAO extends SinglePKDAO[CourseEntity, UUID] {
  /**
   * Вставка связей входных Листов и курса
   * table: course_input_leaf_link
   *
   * @param course курс с которым необходимо создать связи
   */
  def insertInputLeafs(course: CourseEntity)
                      (implicit session: DBSession): Unit

  /**
   * Выборка входных Листов курса
   * table: course_input_leaf_link
   *
   * @param course курс, к которому ищутся листы
   * @return входные листы
   */
  def findInputLeafs(course: CourseTable)
                    (implicit session: DBSession): Seq[CQCElementEntity]

  /**
   * Удаление связи курса и вхродных листов
   *
   * @param course курс связи которого надо удалить
   */
  def deleteInputLeafs(course: CourseEntity)
                      (implicit session: DBSession): Unit

  /**
   * Вставка связей выходных Листов и курса
   * table: course_output_leaf_link
   *
   * @param course курс с которым необходим создать связи
   */
  def insertOutputLeafs(course: CourseEntity)
                       (implicit session: DBSession): Unit

  /**
   * Нахождение выходных Листов курса
   *
   * @param course курс, к которому ищутся листы
   * @return выходные листы
   */
  def findOutputLeafs(course: CourseTable)
                     (implicit session: DBSession): Seq[CQCElementEntity]

  /**
   * Удаление связей курса и выходных Листов
   *
   * @param course курс связи с которым необходимо удалить
   */
  def deleteOutputLeafs(course: CourseEntity)
                       (implicit session: DBSession): Unit

  /**
   * Вставка связей курса с входными и выходными листами
   * @param course курс связи с которым необходимо вставить
   */
  def insertLeafs(course: CourseEntity)
                    (implicit session: DBSession): Unit

  /**
   * Удаление связей курса с входными и выходными листами
   * @param course курс связи с которым необходимо удалить
   */
  def deleteLeafs(course: CourseEntity)
                 (implicit session: DBSession): Unit
}