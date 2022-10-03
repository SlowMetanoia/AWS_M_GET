import Database.DataModel.Model.{CQCDictionary, CQCHierarchy}
import Database.Service.{CQCDictionaryService, CQCHierarchyService}
import scalikejdbc.config.DBs

object Main extends App {
  val dbName = "default"
  DBs.setupAll()
  val cqcDictElements = Seq(
    CQCDictionary("Компетенция"),
    CQCDictionary("Индикатор"),
    CQCDictionary("Знание"),
    CQCDictionary("Умение"),
    CQCDictionary("Навык")
  )

  CQCDictionaryService.insertMultiRows(cqcDictElements)(dbName = dbName)

  val model = CQCHierarchy(childType = "Индикатор", parentType = "Компетенция")
  CQCHierarchyService.insert(model)(dbName = dbName)
  DBs.closeAll()
}
