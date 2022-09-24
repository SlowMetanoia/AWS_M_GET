package Database.Model.Entity

import Database.Mapper.CQCDictionaryMapper
import Database.Model.Table.CQCDictionaryTable
import Database.Signature.EntityAndTable.CQCDictionaryEntitySignature
import CQCDictionaryTable.{cqcDict, cqcDictC}
import Database.Model.EntityModel
import scalikejdbc._

case class CQCDictionaryEntity(name: String) extends CQCDictionaryEntitySignature with EntityModel

object CQCDictionaryEntity extends CQCDictionaryDAO {
  /**
   * Получение всех Видов элементов ККХ из таблицы
   *
   * @param limit   кол-во записей которые необходимо получить
   * @param offset  отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort    порядок сортировки
   * @return последовательность всех Видов элементов ККХ из таблицы
   */
  override def findAll(limit: Int,
                       offset: Int,
                       orderBy: SQLSyntax,
                       sort: SQLSyntax)
                      (implicit session: DBSession): Seq[CQCDictionaryEntity] = {
    val rows: Seq[CQCDictionaryTable] =
      withSQL {
        select.all(cqcDict).from(CQCDictionaryTable as cqcDict)
          .orderBy(orderBy)
          .limit(limit)
          .offset(offset)
      }.map(CQCDictionaryTable(cqcDict.resultName)).collection.apply()

    rows.map(CQCDictionaryMapper.tableRow2Entity)
  }

  /**
   * Получение Видов элементов ККХ из таблицы по id
   *
   * @param id Вида элемента ККХ который необходимо получить
   * @return Optional с Видом элементов ККХ
   */
  override def findById(id: String)
                       (implicit session: DBSession): Option[CQCDictionaryEntity] = {
    val row: Option[CQCDictionaryTable] =
      withSQL {
        select.from(CQCDictionaryTable as cqcDict)
          .where.eq(cqcDict.name, id)
      }.map(CQCDictionaryTable(cqcDict.resultName)).single.apply()

    row.map(CQCDictionaryMapper.tableRow2Entity)
  }

  /**
   * Вставка нового Вида элемента ККХ в таблицу
   *
   * @param entity Вид элемента ККХ который необходимо вставить в таблицу
   */
  override def insert(entity: CQCDictionaryEntity)
                     (implicit session: DBSession): Unit = {
    val row = CQCDictionaryMapper.entity2TableRow(entity)
    withSQL {
      insertInto(CQCDictionaryTable)
        .namedValues(
          cqcDictC.name -> row.name
        )
    }.update.apply()
  }

  /**
   * Вставка сразу нескольких Видов элементов ККХ в БД
   *
   * @param entityList список KAS'ов которые мы хотим вставить
   */
  override def insertMultiRows(entityList: Seq[CQCDictionaryEntity])
                              (implicit session: DBSession): Unit = {
    val batchParams: Seq[Seq[Any]] = entityList.map(CQCDictionaryMapper.entity2TableRow)
      .map(elem => Seq(elem.name))

    withSQL {
      insertInto(CQCDictionaryTable)
        .namedValues(
          cqcDictC.name -> sqls.?
        )
    }.batch(batchParams: _*).apply()
  }

  /**
   * Удаление Вида элемента ККХ из таблицы по id
   *
   * @param id Entity которую необходимо удалить
   */
  override def deleteById(id: String)
                         (implicit session: DBSession): Unit =
    withSQL {
      deleteFrom(CQCDictionaryTable)
        .where.eq(cqcDictC.name, id)
    }.update.apply()


  /**
   * Обновление Вида элемента ККХ в таблице
   *
   * @param entity Вид элемента ККХ которое будет обновлено
   */
  override def update(entity: CQCDictionaryEntity)
                     (implicit session: DBSession): Unit = {
    val row = CQCDictionaryMapper.entity2TableRow(entity)
    withSQL {
      QueryDSL.update(CQCDictionaryTable)
        .set(
          cqcDictC.name -> row.name,
        )
    }.update.apply()
  }
}
