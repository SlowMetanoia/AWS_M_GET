package Database.Mapper

import Database.DataModel.Entity.CQCDictionaryEntity
import Database.DataModel.Model.CQCDictionary
import Database.DataModel.Table.CQCDictionaryTable
import org.specs2.mutable.Specification

object CQCDictionaryMapperTest extends Specification {
  val entity: CQCDictionaryEntity = CQCDictionaryEntity(
    name = "Компетенция"
  )

  "from table row to entity" in {
    CQCDictionaryMapper.tableRow2Entity(CQCDictionaryTable(
      name = entity.name
    )) mustEqual entity
  }

  "from entity to table row" in {
    CQCDictionaryMapper.entity2TableRow(entity) mustEqual CQCDictionaryTable(
      name = entity.name
    )
  }

  "from entity to model" in {
    CQCDictionaryMapper.entity2Model(entity) mustEqual CQCDictionary(
      name = entity.name
    )
  }

  "from model to entity" in {
    CQCDictionaryMapper.model2Entity(CQCDictionary(
      name = entity.name
    )) mustEqual entity
  }
}
