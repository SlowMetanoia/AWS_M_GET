package Database.DataModel.Entity

import Database.DataModel.Entity.Setting.{AutoRollbackNamedDB, BeforeAfterAllDBInit}
import Database.{DESC, Name}

import java.sql.SQLException

object CQCDictionaryEntityTest extends BeforeAfterAllDBInit {
  val entity: CQCDictionaryEntity = CQCDictionaryEntity("Компетенция")

  "Dictionary element successfully created" in new AutoRollbackNamedDB {
    CQCDictionaryEntity.insert(entity)

    val res: Option[CQCDictionaryEntity] = CQCDictionaryEntity.findById(entity.name)
    res.isDefined must beTrue
    res.get mustEqual entity
  }

  "Dictionary element does not created, because such PK exist" in new AutoRollbackNamedDB {
    CQCDictionaryEntity.insert(entity)
    CQCDictionaryEntity.insert(entity) must throwA[SQLException]
  }

  "Dictionary elements successfully created" in new AutoRollbackNamedDB {
    val entities: Seq[CQCDictionaryEntity] = for (i <- 1 to 10) yield
      CQCDictionaryEntity(
        name = s"Компетенция$i"
      )
    CQCDictionaryEntity.insertMultiRows(entities)

    val res: Seq[CQCDictionaryEntity] = CQCDictionaryEntity.findAll()

    res.nonEmpty must beTrue
    res.size mustEqual entities.size
    res mustEqual entities.sortBy(_.name)
  }

  "Dictionary elements does not created, because such PK exist" in new AutoRollbackNamedDB {
    val entities: Seq[CQCDictionaryEntity] = for (_ <- 1 to 10) yield entity

    CQCDictionaryEntity.insertMultiRows(entities) must throwA[SQLException]
  }

  "Select dictionary element from table by id" in new AutoRollbackNamedDB {
    CQCDictionaryEntity.insert(entity)

    val res: Option[CQCDictionaryEntity] = CQCDictionaryEntity.findById(entity.name)

    res.isDefined must beTrue
    res.get mustEqual entity
  }

  "Empty select, because PK does not exist" in new AutoRollbackNamedDB {
    CQCDictionaryEntity.insert(entity)

    val res: Option[CQCDictionaryEntity] = CQCDictionaryEntity.findById("Курс")

    res.isEmpty must beTrue
  }

  "Select all dictionary elements without parameters" in new AutoRollbackNamedDB {
    val entities: Seq[CQCDictionaryEntity] = for (i <- 1 to 10) yield
      CQCDictionaryEntity(
        name = s"Компетенция$i"
      )
    CQCDictionaryEntity.insertMultiRows(entities)

    val res: Seq[CQCDictionaryEntity] = CQCDictionaryEntity.findAll()
    res.nonEmpty must beTrue
    res.size mustEqual entities.size
    res mustEqual entities.sortBy(_.name)
  }

  "Select all dictionary elements with limit" in new AutoRollbackNamedDB {
    val limit = 5
    val entities: Seq[CQCDictionaryEntity] = for (i <- 1 to 10) yield
      CQCDictionaryEntity(
        name = s"Компетенция$i"
      )
    CQCDictionaryEntity.insertMultiRows(entities)

    val res: Seq[CQCDictionaryEntity] = CQCDictionaryEntity.findAll(
      limit = limit
    )
    res.nonEmpty must beTrue
    res.size mustEqual limit
    res mustEqual entities.sortBy(_.name).take(5)
  }

  "Select all dictionary elements with all parameters" in new AutoRollbackNamedDB {
    val limit = 7
    val offset = 2
    val entities: Seq[CQCDictionaryEntity] = for (i <- 1 to 10) yield
      CQCDictionaryEntity(
        name = s"Компетенция$i"
      )
    CQCDictionaryEntity.insertMultiRows(entities)

    val res: Seq[CQCDictionaryEntity] = CQCDictionaryEntity.findAll(
      limit = limit,
      offset = offset,
      orderBy = Name.value,
      sort = DESC.value
    )

    res.nonEmpty must beTrue
    res.size mustEqual 7
    res mustEqual entities.sortBy(_.name).slice(2, entities.size - 1)
  }

  "Dictionary element update successfully" in new AutoRollbackNamedDB {
    CQCDictionaryEntity.insert(entity)

    val updatedData: CQCDictionaryEntity = CQCDictionaryEntity(
      name = "Не компеценция"
    )

    CQCDictionaryEntity.update(updatedData)
    val res: Option[CQCDictionaryEntity] = CQCDictionaryEntity.findById(updatedData.name)

    res.isDefined must beTrue
    res.get mustEqual updatedData
  }

  "Dictionary element delete successfully" in new AutoRollbackNamedDB {
    CQCDictionaryEntity.insert(entity)
    CQCDictionaryEntity.deleteById(entity.name)

    val res: Option[CQCDictionaryEntity] = CQCDictionaryEntity.findById(entity.name)

    res.isEmpty must beTrue
  }
}
