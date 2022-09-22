package Database.Model.Entity

import Database.Mapper.CQCElementMapper
import Database.Model.HierarchyEntityModel
import Database.Model.Table.CQCElementTable
import Database.Model.Table.CQCElementTable.{cqc, cqcC}
import Database.Signature.EntityAndTable.CQCElementEntitySignature
import scalikejdbc._

import java.util.UUID

case class CQCElementEntity(id: UUID,
                            parentId: UUID,
                            elemType: String,
                            value: String) extends CQCElementEntitySignature with HierarchyEntityModel {
  /**
   * Получение родителя Элемента ККХ
   *
   * @return родителя
   */
  override def parent(implicit session: DBSession): Option[CQCElementEntity] = {
    val row: Option[CQCElementTable] =
      withSQL {
      select.from(CQCElementTable as cqc)
        .where.eq(cqc.parentId, parentId)
    }.map(CQCElementTable(cqc.resultName)).single.apply()

    row.map(CQCElementMapper.tableRow2Entity)
  }

  /**
   * Получением потомков Элемента ККХ
   *
   * @return последовательноть потомков Элемента ККХ
   */
  override def children(implicit session: DBSession): Seq[CQCElementEntity] = {
    val rows: Seq[CQCElementTable] =
      withSQL {
        select.all(cqc).from(CQCElementTable as cqc)
          .where.eq(cqc.parentId, id)
      }.map(CQCElementTable(cqc.resultName)).collection.apply()

    rows.map(CQCElementMapper.tableRow2Entity)
  }
}

object CQCElementEntity extends CQCElementDAO {

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
    val rows: Seq[CQCElementTable] =
      withSQL {
        select.all(cqc).from(CQCElementTable as cqc)
          .orderBy(orderBy)
          .limit(limit)
          .offset(offset)
      }.map(CQCElementTable(cqc.resultName)).collection.apply()

    rows.map(CQCElementMapper.tableRow2Entity)
  }

  /**
   * Получение Элементов ККХ из таблицы по id
   *
   * @param id Элемент ККХ который необходимо получить
   * @return Optional с Элементом ККХ
   */
  override def findById(id: UUID)(implicit session: DBSession): Option[CQCElementEntity] = {
    val row: Option[CQCElementTable] =
      withSQL {
        select.from(CQCElementTable as cqc)
          .where.eq(cqc.id, id)
      }.map(CQCElementTable(cqc.resultName)).single.apply()

    row.map(CQCElementMapper.tableRow2Entity)
  }


  /**
   * Вставка нового Элемента ККХ в таблицу
   *
   * @param entity который необходимо вставить в таблицу
   */
  override def insert(entity: CQCElementEntity)
                     (implicit session: DBSession): Unit = {
    val rows = CQCElementMapper.entity2TableRow(entity)
    withSQL {
      insertInto(CQCElementTable)
        .namedValues(
          cqcC.id -> rows.id,
          cqcC.parentId -> rows.parentId,
          cqcC.elemType -> rows.elemType,
          cqcC.value -> rows.value
        )
    }.update.apply()
  }

  /**
   * Вставка сразу нескольких Элементов ККХ в БД
   *
   * @param entityList список Элементов ККХ которые мы хотим вставить
   */
  override def insertMultiRows(entityList: Seq[CQCElementEntity])
                              (implicit session: DBSession): Unit = {
    val batchParams: Seq[Seq[Any]] = entityList.map(CQCElementMapper.entity2TableRow)
      .map(elem => Seq(elem.id, elem.parentId, elem.elemType))

    withSQL {
      insertInto(CQCElementTable)
        .namedValues(
          cqcC.id -> sqls.?,
          cqcC.parentId -> sqls.?,
          cqcC.elemType -> sqls.?,
        )
    }.batch(batchParams: _*).apply()
  }

  /**
   * Удаление Элемента ККХ из таблицы по id
   *
   * @param id Элемента ККХ который необходимо удалить
   */
  override def deleteById(id: UUID)
                         (implicit session: DBSession): Unit =
    withSQL {
      deleteFrom(CQCElementTable)
        .where.eq(cqcC.id, id)
    }.update.apply()

  /**
   * Обновление Элемента ККХ в таблице
   *
   * @param entity Элемента ККХ который будет обновлен
   */
  override def update(entity: CQCElementEntity)
                     (implicit session: DBSession): Unit = {
    val row = CQCElementMapper.entity2TableRow(entity)
    withSQL {
      QueryDSL.update(CQCElementTable)
        .set(
          cqcC.parentId -> row.parentId,
          cqcC.elemType -> row.elemType
        )
    }.update.apply()
  }
}
