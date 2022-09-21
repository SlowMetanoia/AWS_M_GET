package Database.Model.Entity

import Database.Mapper.CQCElementDictionaryMapper
import Database.Table.CQCElementDictionaryTable
import Database.Table.CQCElementDictionaryTable.{cqcDict, cqcDictC}
import scalikejdbc._

case class CQCElementDictionaryEntity(name: String) extends CQCElementDictionaryEntitySignature

object CQCElementDictionaryEntity extends CQCElementDictionaryDAO {
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
                      (implicit session: DBSession): Seq[CQCElementDictionaryEntity] = {
    val rows: Seq[CQCElementDictionaryTable] =
      withSQL {
        select.all(cqcDict).from(CQCElementDictionaryTable as cqcDict)
          .orderBy(orderBy)
          .limit(limit)
          .offset(offset)
      }.map(CQCElementDictionaryTable(cqcDict.resultName)).collection.apply()

    rows.map(CQCElementDictionaryMapper.tableRow2Entity)
  }

  /**
   * Получение Видов элементов ККХ из таблицы по id
   *
   * @param id Вида элемента ККХ который необходимо получить
   * @return Optional с Видом элементов ККХ
   */
  override def findById(id: String)
                       (implicit session: DBSession): Option[CQCElementDictionaryEntity] = {
    val row: Option[CQCElementDictionaryTable] =
      withSQL {
        select.from(CQCElementDictionaryTable as cqcDict)
          .where.eq(cqcDict.name, id)
      }.map(CQCElementDictionaryTable(cqcDict.resultName)).single.apply()

    row.map(CQCElementDictionaryMapper.tableRow2Entity)
  }

  /**
   * Вставка нового Вида элемента ККХ в таблицу
   *
   * @param entity Вид элемента ККХ который необходимо вставить в таблицу
   */
  override def insert(entity: CQCElementDictionaryEntity)
                     (implicit session: DBSession): Unit = {
    val row = CQCElementDictionaryMapper.entity2TableRow(entity)
    withSQL {
      insertInto(CQCElementDictionaryTable)
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
  override def insertMultiRows(entityList: Seq[CQCElementDictionaryEntity])
                              (implicit session: DBSession): Unit = {
    val batchParams: Seq[Seq[Any]] = entityList.map(CQCElementDictionaryMapper.entity2TableRow)
      .map(elem => Seq(elem.name))

    withSQL {
      insertInto(CQCElementDictionaryTable)
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
      deleteFrom(CQCElementDictionaryTable)
        .where.eq(cqcDictC.name, id)
    }.update.apply()


  /**
   * Обновление Вида элемента ККХ в таблице
   *
   * @param entity Вид элемента ККХ которое будет обновлено
   */
  override def update(entity: CQCElementDictionaryEntity)
                     (implicit session: DBSession): Unit = {
    val row = CQCElementDictionaryMapper.entity2TableRow(entity)
    withSQL {
      QueryDSL.update(CQCElementDictionaryTable)
        .set(
          cqcDictC.name -> row.name,
        )
    }.update.apply()
  }
}
