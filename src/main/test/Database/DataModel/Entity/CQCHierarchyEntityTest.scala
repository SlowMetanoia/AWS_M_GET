package Database.DataModel.Entity

import Database.DataModel.Entity.Setting.{AutoRollbackNamedDB, BeforeAfterAllDBInit}
import scalikejdbc.NamedDB

object CQCHierarchyEntityTest extends BeforeAfterAllDBInit {
  val cqcDictElements: Seq[CQCDictionaryEntity] = Seq(
    CQCDictionaryEntity("Компетенция"),
    CQCDictionaryEntity("Индикатор"),
    CQCDictionaryEntity("Знание"),
    CQCDictionaryEntity("Умение"),
    CQCDictionaryEntity("Навык")
  )

  val entity: CQCHierarchyEntity = CQCHierarchyEntity("Индикатор", "Компетенция")

  override def beforeAll(): Unit = {
    super.beforeAll()

    NamedDB(DBName) localTx { implicit session =>
      CQCDictionaryEntity.insertMultiRows(cqcDictElements)
    }
  }

  override def afterAll(): Unit = {
    NamedDB(DBName) localTx { implicit session =>
      cqcDictElements.foreach(elem => CQCDictionaryEntity.deleteById(elem.name))
    }

  }

  sequential

  "Hierarchy successfully created" in new AutoRollbackNamedDB {
    CQCHierarchyEntity.insert(entity)

    val res: Option[CQCHierarchyEntity] = CQCHierarchyEntity.findByDoubleId((entity.childType, entity.parentType))
    res.isDefined must beTrue
    res.get mustEqual entity
  }


}
