package Database.Mapper
import Database.Model.Entity.CQCElementDictionaryEntity
import Database.Table.CQCElementDictionaryTable

object CQCElementDictionaryMapper extends CQCElementDictionaryEntityMapper {
  override def tableRow2Entity(row: CQCElementDictionaryTable): CQCElementDictionaryEntity =
    CQCElementDictionaryEntity(
      name = row.name
    )

  override def entity2TableRow(entity: CQCElementDictionaryEntity): CQCElementDictionaryTable =
    CQCElementDictionaryTable(
      name = entity.name
    )
}
