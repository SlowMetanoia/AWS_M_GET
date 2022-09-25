package Database.Mapper

import Database.DataModel.Model.CQCDictionary
import Database.DataModel.Entity.CQCDictionaryEntity
import Database.DataModel.Table.CQCDictionaryTable

object CQCDictionaryMapper extends CQCDictionaryDataMapper {
  override def tableRow2Entity(row: CQCDictionaryTable): CQCDictionaryEntity =
    CQCDictionaryEntity(
      name = row.name
    )

  override def entity2TableRow(entity: CQCDictionaryEntity): CQCDictionaryTable =
    CQCDictionaryTable(
      name = entity.name
    )

  override def entity2Model(entity: CQCDictionaryEntity): CQCDictionary =
    CQCDictionary(
      name = entity.name
    )

  override def model2Entity(model: CQCDictionary): CQCDictionaryEntity =
    CQCDictionaryEntity(
      name = model.name
    )
}
