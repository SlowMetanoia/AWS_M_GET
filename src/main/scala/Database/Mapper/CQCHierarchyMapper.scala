package Database.Mapper
import Database.DataModel.Model.CQCHierarchy
import Database.DataModel.Entity.CQCHierarchyEntity
import Database.DataModel.Table.CQCHierarchyTable

object CQCHierarchyMapper extends CQCHierarchyDataMapper {
  override def tableRow2Entity(row: CQCHierarchyTable): CQCHierarchyEntity =
    CQCHierarchyEntity(
      childType = row.childType,
      parentType = row.parentType
    )

  override def entity2TableRow(entity: CQCHierarchyEntity): CQCHierarchyTable =
    CQCHierarchyTable(
      childType = entity.childType,
      parentType = entity.parentType
    )

  override def entity2Model(entity: CQCHierarchyEntity): CQCHierarchy =
    CQCHierarchy(
      childType = entity.childType,
      parentType = entity.parentType
    )

  override def model2Entity(model: CQCHierarchy): CQCHierarchyEntity =
    CQCHierarchyEntity(
      childType = model.childType,
      parentType = model.parentType
    )
}
