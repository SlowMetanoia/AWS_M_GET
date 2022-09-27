package Database.Service

import Database.DataModel.Entity.CQCHierarchyEntity
import Database.DataModel.Model.CQCHierarchy
import Database.Mapper.CQCHierarchyMapper
import Database.{SQLOperatorsDictionary, SQLOrderByDictionary}
import scalikejdbc.NamedDB

object CQCHierarchyService extends CQCHierarchyDataService {

  /**
   * Получение текущих отношений между элементами иерархии
   *
   * @return иерархия отношений
   */
  override def relations(dbName: String): Map[String, Set[String]] = {
    findAll()(dbName).groupMap(_.parentType)(_.childType)
  }

  /**
   * Получение всех Уровней иерархии
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
                      (dbName: String): Set[CQCHierarchy] = {
    val entities: Seq[CQCHierarchyEntity] = Seq.empty

    NamedDB(dbName) readOnly { implicit session =>
      entities :+
        CQCHierarchyEntity.findAll(limit,
          offset,
          SQLOrderByDictionary(orderBy),
          SQLOperatorsDictionary(sort))
    }
    entities.toSet.map(CQCHierarchyMapper.entity2Model)
  }

  /**
   * Добавление нового Уровня иерархии
   *
   * @param model которую необходимо добавить
   */
  override def insert(model: CQCHierarchy)
                     (dbName: String): Unit = {
    val res = CQCHierarchyMapper.model2Entity(model)

    NamedDB(dbName) localTx { implicit session =>
      CQCHierarchyEntity.insert(res)
    }
  }

  /**
   * Добавление сразу нескольких Уровней иерархии
   *
   * @param modelList список Model которые мы хотим вставить
   */
  override def insertMultiRows(modelList: Seq[CQCHierarchy])
                              (dbName: String): Unit = {
    val entities = modelList.map(CQCHierarchyMapper.model2Entity)

    NamedDB(dbName) localTx { implicit session =>
      CQCHierarchyEntity.insertMultiRows(entities)
    }
  }

  /**
   * Обновление Уровней иерархии
   *
   * @param model которая будет обновлена
   */
  override def update(model: CQCHierarchy)
                     (dbName: String): Unit = {
    val res = CQCHierarchyMapper.model2Entity(model)

    NamedDB(dbName) localTx { implicit session =>
      CQCHierarchyEntity.update(res)
    }
  }

  /**
   * Получение Уровня иерархии по id
   *
   * @param id Model которую необходимо получить
   * @return Optional с Model
   */
  override def findById(id: (String, String))
                       (dbName: String): Option[CQCHierarchy] = {
    var res: Option[CQCHierarchy] = Option.empty

    NamedDB(dbName) localTx { implicit session =>
      res = CQCHierarchyEntity.findByDoubleId(id).map(CQCHierarchyMapper.entity2Model)
    }
    res
  }

  /**
   * Удаление Уровня иерархии по id
   *
   * @param id Model которую необходимо удалить
   */
  override def deleteById(id: (String, String))
                         (dbName: String): Unit = {
    NamedDB(dbName) localTx { implicit session =>
      CQCHierarchyEntity.deleteByDoubleId(id)
    }
  }
}
