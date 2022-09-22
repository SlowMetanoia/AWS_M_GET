package Database.Mapper
import Database.Model.Entity.CQCDictionaryEntity
import Database.Model.Table.CQCDictionaryTable

object CQCDictionaryMapper extends CQCDictionaryEntityMapper {
  override def tableRow2Entity(row: CQCDictionaryTable): CQCDictionaryEntity =
    CQCDictionaryEntity(
      name = row.name
    )

  override def entity2TableRow(entity: CQCDictionaryEntity): CQCDictionaryTable =
    CQCDictionaryTable(
      name = entity.name
    )
}
