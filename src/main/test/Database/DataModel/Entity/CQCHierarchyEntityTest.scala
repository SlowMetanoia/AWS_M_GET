package Database.DataModel.Entity

import scalikejdbc.NamedDB
import scalikejdbc.specs2.mutable.AutoRollback

object CQCHierarchyEntityTest extends BeforeAfterAllDBInit {
  val cqcDictElements = Seq(
    CQCDictionaryEntity("Компетенция"),
    CQCDictionaryEntity("Индикатор"),
    CQCDictionaryEntity("Знание"),
    CQCDictionaryEntity("Умение"),
    CQCDictionaryEntity("Навык")
  )

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

  val entity: CQCHierarchyEntity = CQCHierarchyEntity("Индикатор", "Компетенция")

  sequential

  "Hierarchy successfully created" in new AutoRollback {
    CQCHierarchyEntity.insert(entity)

    val res: Option[CQCHierarchyEntity] = CQCHierarchyEntity.findByDoubleId((entity.childType, entity.parentType))
    res.isDefined must beTrue
    res.get mustEqual entity
  }
}
