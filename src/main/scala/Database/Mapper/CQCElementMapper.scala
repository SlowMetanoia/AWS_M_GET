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

  override def entity2Model(entity: CQCElementEntity): CQCElement =
    CQCElement(
      id = entity.id,
      parentId = entity.parentId,
      elemType = entity.elemType,
      value = entity.value
    )

  override def model2Entity(model: CQCElement): CQCElementEntity =
    CQCElementEntity(
      id = model.id,
      parentId = model.parentId,
      elemType = model.elemType,
      value = model.value
    )

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

  override def rootModel2Entity(root: CQCElementRoot): CQCElementEntity =
    CQCElementEntity(
      id = root.id,
      parentId = null,
      elemType = root.elemType,
      value = root.value
    )

  override def leafModel2Entity(leaf: CQCElementLeaf): CQCElementEntity =
    CQCElementEntity(
      id = leaf.id,
      parentId = leaf.parentId,
      elemType = leaf.elemType,
      value = leaf.value
    )
}
