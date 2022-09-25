package Database.DataModel.Entity

import Database.Mapper.CQCHierarchyMapper
import Database.DataModel.Table.CQCHierarchyTable
import Database.Signature.EntityAndTable.CQCHierarchyEntitySignature
import CQCHierarchyTable.{cqcHier, cqcHierC}
import Database.DataModel.EntityModel
import scalikejdbc._

case class CQCHierarchyEntity(childType: String,
                              parentType: String) extends CQCHierarchyEntitySignature with EntityModel

object CQCHierarchyEntity extends CQCHierarchyDAO {

  /**
   * Получение Уровня иерархии из таблицы по id
   *
   * @param id Уровня иерархии который необходимо получить
   * @return Optional с Уровнем иерархии
   */
  override def findByDoubleId(id: (String, String))
                             (implicit session: DBSession): Option[CQCHierarchyEntity] = {
    val row: Option[CQCHierarchyTable] =
      withSQL {
      select.from(CQCHierarchyTable as cqcHier)
        .where.eq(cqcHier.childType, id._1)
        .and
        .eq(cqcHier.parentType, id._2)
    }.map(CQCHierarchyTable(cqcHier.resultName)).single.apply()

    row.map(CQCHierarchyMapper.tableRow2Entity)
  }

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
                      (implicit session: DBSession): Seq[CQCHierarchyEntity] = {
    val rows: Seq[CQCHierarchyTable] =
      withSQL {
        select.all(cqcHier).from(CQCHierarchyTable as cqcHier)
          .orderBy(orderBy)
          .limit(limit)
          .offset(offset)
      }.map(CQCHierarchyTable(cqcHier.resultName)).collection.apply()

    rows.map(CQCHierarchyMapper.tableRow2Entity)
  }

  /**
   * Вставка нового Уровня иерархии в таблицу
   *
   * @param entity Уровень иерархии который необходимо вставить в таблицу
   */
  override def insert(entity: CQCHierarchyEntity)
                     (implicit session: DBSession): Unit = {
    val rows = CQCHierarchyMapper.entity2TableRow(entity)
    withSQL {
      insertInto(CQCHierarchyTable)
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
  override def insertMultiRows(entityList: Seq[CQCHierarchyEntity])
                              (implicit session: DBSession): Unit = {
    val batchParams: Seq[Seq[Any]] = entityList.map(CQCHierarchyMapper.entity2TableRow)
      .map(elem => Seq(elem.childType, elem.parentType))

    withSQL {
      insertInto(CQCHierarchyTable)
        .namedValues(
          cqcHierC.childType -> sqls.?,
          cqcHierC.parentType -> sqls.?,
        )
    }.batch(batchParams: _*).apply()
  }

  /**
   * Обновление Уровня иерархии в таблице
   *
   * @param entity Уровень иерархии который будет обновлен
   */
  override def update(entity: CQCHierarchyEntity)
                     (implicit session: DBSession): Unit = {
    val row = CQCHierarchyMapper.entity2TableRow(entity)
    withSQL {
      QueryDSL.update(CQCHierarchyTable)
        .set(
          cqcHierC.childType -> row.childType,
          cqcHierC.parentType -> row.parentType,
        )
    }.update.apply()
  }

  /**
   * Удаление Entity из таблицы по id
   *
   * @param id Entity которую необходимо удалить
   */
  override def deleteByDoubleId(id: (String, String))
                               (implicit session: DBSession): Unit =
    withSQL {
      deleteFrom(CQCHierarchyTable)
        .where.eq(cqcHier.childType, id._1)
        .and
        .eq(cqcHier.parentType, id._2)
    }.update.apply()
}
