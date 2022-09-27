package Database.Service

import Database.DataModel.Entity.CourseEntity
import Database.DataModel.Model.Course
import Database.Mapper.CourseMapper
import Database.{SQLOperatorsDictionary, SQLOrderByDictionary}
import scalikejdbc.NamedDB

import java.util.UUID

object CourseService extends CourseDataService {
  /**
   * Получение всех Курсов
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
                      (dbName: String): Set[Course] = {
    val entities: Seq[CourseEntity] = Seq.empty

    NamedDB(dbName) readOnly { implicit session =>
      entities :+
        CourseEntity.findAll(limit,
          offset,
          SQLOrderByDictionary(orderBy),
          SQLOperatorsDictionary(sort))

      entities.toSet.map(course => CourseMapper.entity2Model(
        course,
        course.parts,
        CQCHierarchyService.relations(dbName)))
    }
  }

  /**
   * Добавление нового Курса
   *
   * @param model которую необходимо добавить
   */
  override def insert(model: Course)
                     (dbName: String): Unit = {
    val res = CourseMapper.model2Entity(model)

    NamedDB(dbName) localTx { implicit session =>
      CourseEntity.insert(res)
    }
  }

  /**
   * Добавление сразу нескольких Курсов
   *
   * @param modelList список Model которые мы хотим вставить
   */
  override def insertMultiRows(modelList: Seq[Course])
                              (dbName: String): Unit = {
    val entities = modelList.map(CourseMapper.model2Entity)

    NamedDB(dbName) localTx { implicit session =>
      CourseEntity.insertMultiRows(entities)
    }
  }

  /**
   * Обновление Курса
   *
   * @param model которая будет обновлена
   */
  override def update(model: Course)
                     (dbName: String): Unit = {
    val res = CourseMapper.model2Entity(model)

    NamedDB(dbName) localTx { implicit session =>
      CourseEntity.update(res)
    }
  }

  /**
   * Получение Курса по id
   *
   * @param id Model которую необходимо получить
   * @return Optional с Model
   */
  override def findById(id: UUID)
                       (dbName: String): Option[Course] = {
    var res: Option[Course] = Option.empty

    NamedDB(dbName) localTx { implicit session =>
      res = CourseEntity.findById(id).map(course => CourseMapper.entity2Model(course,
        course.parts,
        CQCHierarchyService.relations(dbName)))
    }
    res
  }

  /**
   * Удаление Курса по id
   *
   * @param id Model которую необходимо удалить
   */
  override def deleteById(id: UUID)
                         (dbName: String): Unit =
    NamedDB(dbName) localTx { implicit session =>
      CourseEntity.deleteById(id)
    }

}
