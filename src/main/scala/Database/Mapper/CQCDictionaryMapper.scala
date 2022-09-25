package Database.Mapper

import Database.DataModel.Domain.CQCDictionaryModel
import Database.DataModel.Entity.CQCDictionaryEntity
import Database.DataModel.Table.CQCDictionaryTable

object CQCDictionaryMapper extends CQCDictionaryEntityMapper {
  override def tableRow2Entity(row: CQCDictionaryTable): CQCDictionaryEntity =
    CQCDictionaryEntity(
      name = row.name
    )

  override def entity2TableRow(entity: CQCDictionaryEntity): CQCDictionaryTable =
    CQCDictionaryTable(
      name = entity.name
    )

  override def entity2Model(entity: CQCDictionaryEntity): CQCDictionaryModel =
    CQCDictionaryModel(
      name = entity.name
    )

  override def model2Entity(model: CQCDictionaryModel): CQCDictionaryEntity =
    CQCDictionaryEntity(
      name = model.name
    )
}
