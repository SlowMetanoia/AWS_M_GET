package Database.Mapper
import Database.Model.Entity.CQCElementHierarchyEntity
import Database.Table.CQCElementHierarchyTable

object CQCElementHierarchyMapper extends CQCElementHierarchyEntityMapper {
  override def tableRow2Entity(row: CQCElementHierarchyTable): CQCElementHierarchyEntity =
    CQCElementHierarchyEntity(
      childType = row.childType,
      parentType = row.parentType
    )

  override def entity2TableRow(entity: CQCElementHierarchyEntity): CQCElementHierarchyTable =
    CQCElementHierarchyTable(
      childType = entity.childType,
      parentType = entity.parentType
    )
}
