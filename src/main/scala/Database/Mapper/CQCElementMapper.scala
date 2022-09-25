package Database.Mapper
import Database.DataModel.Model.CQCElement.{CQCElement, CQCElementLeaf, CQCElementRoot}
import Database.DataModel.Entity.CQCElementEntity
import Database.DataModel.Table.CQCElementTable

object CQCElementMapper extends CQCElementDataMapper {
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

  override def entity2Model(entity: CQCElementEntity): CQCElement = ???

  override def model2Entity(model: CQCElement): CQCElementEntity = ???

  override def entity2RootModel(entity: CQCElementEntity): CQCElementRoot = {
    CQCElementRoot(
      id = entity.id,
      elemType = entity.elemType,
      value = entity.value
    )
  }

  override def entity2LeafModel(entity: CQCElementEntity): CQCElementLeaf =
    CQCElementLeaf(
      id = entity.id,
      parentId = entity.parentId,
      elemType = entity.elemType,
      value = entity.value
    )
}
