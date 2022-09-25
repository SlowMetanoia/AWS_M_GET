package Database.Service
import Database.DataModel.Entity.CQCElementEntity
import Database.DataModel.Model.CQCElement.CQCElement
import Database.Mapper.CQCElementMapper
import Database.{SQLOperatorsDictionary, SQLOrderByDictionary}
import scalikejdbc.NamedDB

import java.util.UUID

object CQCElementService extends CQCElementDataService {
  /**
   * Получение всех Элементов ККХ
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
                      (dbName: String): Set[CQCElement] = {
    val entities: Seq[CQCElementEntity] = Seq.empty

    NamedDB(dbName) readOnly { implicit session =>
      entities :+
        CQCElementEntity.findAll(limit,
          offset,
          SQLOrderByDictionary(orderBy),
          SQLOperatorsDictionary(sort))
    }
    entities.toSet.map(CQCElementMapper.entity2Model)
  }

  /**
   * Добавление нового Элемента ККХ
   *
   * @param model которую необходимо добавить
   */
  override def insert(model: CQCElement)
                     (dbName: String): Unit = {
    val res = CQCElementMapper.model2Entity(model)

    NamedDB(dbName) localTx { implicit session =>
      CQCElementEntity.insert(res)
    }
  }

  /**
   * Добавление сразу нескольких Элементов ККХ
   *
   * @param modelList список Model которые мы хотим вставить
   */
  override def insertMultiRows(modelList: Seq[CQCElement])
                              (dbName: String): Unit = {
    val entities = modelList.map(CQCElementMapper.model2Entity)

    NamedDB(dbName) localTx { implicit session =>
      CQCElementEntity.insertMultiRows(entities)
    }
  }

  /**
   * Обновление Элемента ККХ
   *
   * @param model которая будет обновлена
   */
  override def update(model: CQCElement)
                     (dbName: String): Unit = {
    val res = CQCElementMapper.model2Entity(model)

    NamedDB(dbName) localTx { implicit session =>
      CQCElementEntity.update(res)
    }
  }

  /**
   * Получение Элемента ККХ по id
   *
   * @param id Model которую необходимо получить
   * @return Optional с Model
   */
  override def findById(id: UUID)
                       (dbName: String): Option[CQCElement] = {
    var res: Option[CQCElement] = Option.empty

    NamedDB(dbName) localTx { implicit session =>
      res = CQCElementEntity.findById(id).map(CQCElementMapper.entity2Model)
    }
    res
  }

  /**
   * Удаление Вида ККХ по id
   *
   * @param id Model которую необходимо удалить
   */
  override def deleteById(id: UUID)
                         (dbName: String): Unit = {
    NamedDB(dbName) localTx { implicit session =>
      CQCElementEntity.deleteById(id)
    }
  }
}
