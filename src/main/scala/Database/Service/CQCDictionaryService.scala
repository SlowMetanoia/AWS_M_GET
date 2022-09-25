package Database.Service

import Database.DataModel.Entity.CQCDictionaryEntity
import Database.DataModel.Model.CQCDictionary
import Database.Mapper.CQCDictionaryMapper
import Database.{SQLOperatorsDictionary, SQLOrderByDictionary}
import scalikejdbc.NamedDB

object CQCDictionaryService extends CQCDictionaryDataService {
  /**
   * Получение всех Видов элементов ККХ
   *
   * @param limit   кол-во записей которые необходимо получить
   * @param offset  отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort    порядок сортировки
   * @return последовательность всех Model
   */
  override def findAll(limit: Int,
                       offset: Int,
                       orderBy: String,
                       sort: String)
                      (dbName: String): Set[CQCDictionary] = {
    val entities: Seq[CQCDictionaryEntity] = Seq.empty

    NamedDB(dbName) readOnly { implicit session =>
      entities :+
        CQCDictionaryEntity.findAll(limit,
          offset,
          SQLOrderByDictionary(orderBy),
          SQLOperatorsDictionary(sort))
    }
    entities.toSet.map(CQCDictionaryMapper.entity2Model)
  }

  /**
   * Добавление нового Вида элементов ККХ
   *
   * @param model которую необходимо добавить
   */
  override def insert(model: CQCDictionary)
                     (dbName: String): Unit = {
    val res = CQCDictionaryMapper.model2Entity(model)

    NamedDB(dbName) localTx { implicit session =>
      CQCDictionaryEntity.insert(res)
    }
  }

  /**
   * Добавление сразу нескольких Видов ККХ
   *
   * @param modelList список Model которые мы хотим вставить
   */
  override def insertMultiRows(modelList: Seq[CQCDictionary])
                              (dbName: String): Unit = {
    val entities = modelList.map(CQCDictionaryMapper.model2Entity)

    NamedDB(dbName) localTx { implicit session =>
      CQCDictionaryEntity.insertMultiRows(entities)
    }
  }

  /**
   * Обновление Вида ККХ
   *
   * @param model которая будет обновлена
   */
  override def update(model: CQCDictionary)
                     (dbName: String): Unit = {
    val res = CQCDictionaryMapper.model2Entity(model)

    NamedDB(dbName) localTx { implicit session =>
      CQCDictionaryEntity.update(res)
    }
  }

  /**
   * Получение Вида ККХ по id
   *
   * @param id Model которую необходимо получить
   * @return Optional с Model
   */
  override def findById(id: String)
                       (dbName: String): Option[CQCDictionary] = {
    var res: Option[CQCDictionary] = Option.empty

    NamedDB(dbName) localTx { implicit session =>
      res = CQCDictionaryEntity.findById(id).map(CQCDictionaryMapper.entity2Model)
    }
    res
  }

  /**
   * Удаление Вида ККХ по id
   *
   * @param id Model которую необходимо удалить
   */
  override def deleteById(id: String)
                         (dbName: String): Unit =
    NamedDB(dbName) localTx { implicit session =>
      CQCDictionaryEntity.deleteById(id)
    }
}
