package Database.Model.Entity

import Database.Mapper.CQCElementMapper
import Database.Table.CQCElementTable
import Database.Table.CQCElementTable.{cqc, cqcC}
import scalikejdbc._

import java.util.UUID

case class CQCElementEntity(id: UUID,
                            parentId: UUID,
                            elemType: String,
                            children: Seq[CQCElementEntity]) extends CQCElementEntityTrait

object CQCElementEntity extends CQCElementDAO {

  override def findChild(entity: CQCElementEntityTrait): Seq[CQCElementEntityTrait] = ???

  /**
   * Получение всех Элементов ККХ из таблицы
   *
   * @param limit   кол-во записей которые необходимо получить
   * @param offset  отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort    порядок сортировки
   * @return последовательность всех Элементов ККХ из таблицы
   */
  override def findAll(limit: Int,
                       offset: Int,
                       orderBy: SQLSyntax,
                       sort: SQLSyntax)
                      (implicit session: DBSession): Seq[CQCElementEntity] = {
    val cqcElemRows: Seq[CQCElementTable] =
      withSQL {
        select.all(cqc).from(CQCElementTable as cqc)
          .orderBy(orderBy)
          .limit(limit)
          .offset(offset)
      }.map(CQCElementTable(cqc.resultName)).collection.apply()

    cqcElemRows.map(CQCElementMapper.tableRow2Entity)
  }

  /**
   * Получение Элементов ККХ из таблицы по id
   *
   * @param id Элемент ККХ который необходимо получить
   * @return Optional с Элементом ККХ
   */
  override def findById(id: UUID)(implicit session: DBSession): Option[CQCElementEntity] = {
    val cqcElementRow: Option[CQCElementTable] =
      withSQL {
        select.from(CQCElementTable as cqc)
          .where.eq(cqc.id, id)
      }.map(CQCElementTable(cqc.resultName)).single.apply()

    cqcElementRow.map(CQCElementMapper.tableRow2Entity)
  }


  /**
   * Вставка нового Элемента ККХ в таблицу
   *
   * @param entity который необходимо вставить в таблицу
   */
  override def insert(entity: CQCElementEntity)
                     (implicit session: DBSession): Unit =
    withSQL {
      insertInto(CQCElementTable)
        .namedValues(
          cqcC.id -> entity.id,
          cqcC.parentId -> entity.parentId,
          cqcC.elementType -> entity.elemType,
        )
    }.update.apply()

  /**
   * Вставка сразу нескольких KAS'ов в БД
   *
   * @param entityList список KAS'ов которые мы хотим вставить
   */
  override def insertMultiRows(entityList: Seq[CQCElementEntity])
                              (implicit session: DBSession): Unit = {
    val batchParams: Seq[Seq[Any]] = entityList.map(elem => Seq(elem.id, elem.parentId, elem.elemType))

    withSQL {
      insertInto(CQCElementTable)
        .namedValues(
          cqcC.id -> sqls.?,
          cqcC.parentId -> sqls.?,
          cqcC.elementType -> sqls.?,
        )
    }.batch(batchParams: _*).apply()
  }

  /**
   * Удаление Entity из таблицы по id
   *
   * @param id Entity которую необходимо удалить
   */
  override def deleteById(id: UUID)(implicit session: DBSession): Unit = ???

  /**
   * Обновление Entity в таблице
   *
   * @param entity Entity которое будет обновлено
   */
  override def update(entity: CQCElementEntity)(implicit session: DBSession): Unit = ???
}
