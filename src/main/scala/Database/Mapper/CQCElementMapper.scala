package Database.Mapper
import Database.Model.Entity
import Database.Model.Entity.CQCElementEntity
import Database.Table.CQCElementTable

object CQCElementMapper extends CQCElementMapper {
  override def tableRow2Entity(row: CQCElementTable): CQCElementEntity =
    CQCElementEntity(
      id = row.id,
      parentId = row.parentId,
      elemType = row.elemType,
      children = Seq.empty[CQCElementEntity]
    )

  override def entity2TableRow(entity: CQCElementEntity): CQCElementTable =
    CQCElementTable(
      id = entity.id,
      parentId = entity.parentId,
      elemType = entity.elemType
    )
}
