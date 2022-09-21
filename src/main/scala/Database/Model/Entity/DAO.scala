package Database.Model.Entity

import Database.{ASC, Id}
import scalikejdbc.{DBSession, ParameterBinderFactory}
import scalikejdbc.interpolation.SQLSyntax

import java.util.UUID

sealed trait DAO[EntityType, IdType] {
  implicit val uuidFactory: ParameterBinderFactory[UUID] = ParameterBinderFactory[UUID] {
    value => (stmt, idx) => stmt.setObject(idx, value)
  }

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
   * Получение Entity из таблицы по id
   *
   * @param id Entity которую необходимо получить
   * @return Optional с Entity если такая есть в БД, иначе Option.empty
   */
  def findById(id: IdType)
              (implicit session: DBSession): Option[EntityType]

  /**
   * Вставка новой Entity в таблицу
   *
   * @param entity Entity которую необходимо вставить в таблицу
   */
  def insert(entity: EntityType)
            (implicit session: DBSession): Unit

  /**
   * Вставка сразу нескольких KAS'ов в БД
   *
   * @param entityList список KAS'ов которые мы хотим вставить
   */
  def insertMultiRows(entityList: Seq[EntityType])
                     (implicit session: DBSession): Unit

  /**
   * Удаление Entity из таблицы по id
   *
   * @param id Entity которую необходимо удалить
   */
  def deleteById(id: IdType)
                (implicit session: DBSession): Unit

  /**
   * Обновление Entity в таблице
   *
   * @param entity Entity которое будет обновлено
   */
  def update(entity: EntityType)
            (implicit session: DBSession): Unit

}

trait CQCElementDAO extends DAO[CQCElementEntity, UUID] {
  def findChild(entity: CQCElementEntityTrait): Seq[CQCElementEntityTrait]
}
