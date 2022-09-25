package Database.Mapper
import Database.DataModel.Domain.CQCHierarchyModel
import Database.DataModel.Entity.CQCHierarchyEntity
import Database.DataModel.Table.CQCHierarchyTable

object CQCHierarchyMapper extends CQCHierarchyEntityMapper {
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

  override def entity2Model(entity: CQCHierarchyEntity): CQCHierarchyModel = ???

  override def model2Entity(model: CQCHierarchyModel): CQCHierarchyEntity = ???
}
