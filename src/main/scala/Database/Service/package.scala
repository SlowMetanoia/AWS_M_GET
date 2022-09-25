package Database

import Database.DataModel.Model.CQCElement.CQCElement
import Database.DataModel.Model.{CQCDictionary, CQCHierarchy, Course}
import Database.Signature.ModelSignature

import java.util.UUID

package object Service {
  sealed trait Service

  sealed trait CRUDService[ModelType <: ModelSignature, IdType] extends Service {
    /**
     * Получение всех Model
     *
     * @param limit   кол-во записей которые необходимо получить
     * @param offset  отсутуп от начала полученных записей
     * @param orderBy поле по которому необходимо отсортировать записи
     * @param sort    порядок сортировки
     * @return последовательность всех Model
     */
    def findAll(limit: Int = 100,
                offset: Int = 0,
                orderBy: String = "id",
                sort: String = "asc")
               (dbName: String = "default"): Set[ModelType]

    /**
     * Добавление новой Model
     *
     * @param model которую необходимо добавить
     */
    def insert(model: ModelType)
              (dbName: String = "default"): Unit

    /**
     * Добавление сразу нескольких Model
     *
     * @param modelList список Model которые мы хотим вставить
     */
    def insertMultiRows(modelList: Seq[ModelType])
                       (dbName: String = "default"): Unit

    /**
     * Обновление Model
     *
     * @param model которая будет обновлена
     */
    def update(model: ModelType)
              (dbName: String = "default"): Unit

    /**
     * Получение Model по id
     *
     * @param id Model которую необходимо получить
     * @return Optional с Model
     */
    def findById(id: IdType)
                (dbName: String = "default"): Option[ModelType]

    /**
     * Удаление Model по id
     *
     * @param id Model которую необходимо удалить
     */
    def deleteById(id: IdType)
                  (dbName: String = "default"): Unit
  }

  trait CourseDataService extends CRUDService[Course, UUID]

  trait CQCElementDataService extends CRUDService[CQCElement, UUID]

  trait CQCDictionaryDataService extends CRUDService[CQCDictionary, String]

  trait CQCHierarchyDataService extends CRUDService[CQCHierarchy, (String, String)] {
    /**
     * Получение текущих отношений между элементами иерархии
     *
     * @return иерархия отношений
     */
    def relations: Map[String, String]
  }
}
