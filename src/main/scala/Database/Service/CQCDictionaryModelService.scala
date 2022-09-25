package Database.Service

import Database.DataModel.Domain.CQCDictionaryModel
import Database.DataModel.Entity.CQCDictionaryEntity
import Database.Mapper.CQCDictionaryMapper
import Database.{SQLOperatorsDictionary, SQLOrderByDictionary}
import scalikejdbc.NamedDB

object CQCDictionaryModelService extends CQCDictionaryService {
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
                      (dbName: String): Set[CQCDictionaryModel] = {
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
  override def insert(model: CQCDictionaryModel)
                     (dbName: String): CQCDictionaryModel = {
    NamedDB(dbName) localTx { implicit session =>
      CQCDictionaryEntity.insert(CQCDictionaryMapper.model2Entity(model))
    }
    model
  }

  /**
   * Добавление сразу нескольких Видов ККХ
   *
   * @param modelList список Model которые мы хотим вставить
   */
  override def insertMultiRows(modelList: Seq[CQCDictionaryModel])
                              (dbName: String): Unit = {
    val entities = modelList.map(CQCDictionaryMapper.model2Entity)
    NamedDB(dbName) localTx { implicit session =>
      CQCDictionaryEntity.insertMultiRows(entities)
    }
  }

  /**
   * Обновление Model
   *
   * @param model которая будет обновлена
   */
  override def update(model: CQCDictionaryModel)
                     (dbName: String): CQCDictionaryModel = {
    NamedDB(dbName) localTx { implicit session =>
      CQCDictionaryEntity.update(CQCDictionaryMapper.model2Entity(model))
    }
    model
  }

  /**
   * Получение Model по id
   *
   * @param id Model которую необходимо получить
   * @return Optional с Model
   */
  override def findById(id: String)
                       (dbName: String): Option[CQCDictionaryModel] = {
    val res = Option[CQCDictionaryModel]
    NamedDB(dbName) localTx { implicit session =>
      res = CQCDictionaryEntity.findById(id)
    }
  }

  /**
   * Удаление Model по id
   *
   * @param id Model которую необходимо удалить
   */
  override def deleteById(id: String)
                         (dbName: String): Unit = ???
}
