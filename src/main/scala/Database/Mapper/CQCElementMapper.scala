package Database.Mapper
import Database.DataModel.Domain.CQCElement.CQCPartModel
import Database.DataModel.Entity.CQCElementEntity
import Database.DataModel.Table.CQCElementTable

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

  override def entity2Model(entity: CQCElementEntity): CQCPartModel = ???

  override def model2Entity(model: CQCPartModel): CQCElementEntity = ???
}
