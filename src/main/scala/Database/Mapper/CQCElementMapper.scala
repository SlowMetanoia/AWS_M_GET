package Database.Mapper
import Database.Model.Entity.CQCElementEntity
import Database.Model.Table.CQCElementTable

object CQCElementMapper extends CQCElementEntityMapper {
  override def tableRow2Entity(row: CQCElementTable): CQCElementEntity =
    CQCElementEntity(
      id = row.id,
      parentId = row.parentId,
      elemType = row.elemType,
      value = row.value
    )

  override def entity2TableRow(entity: CQCElementEntity): CQCElementTable =
    CQCElementTable(
      id = entity.id,
      parentId = entity.parentId,
      elemType = entity.elemType,
      value = entity.value
    )
}
