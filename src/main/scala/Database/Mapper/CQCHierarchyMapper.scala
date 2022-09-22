package Database.Mapper
import Database.Model.Entity.CQCHierarchyEntity
import Database.Model.Table.CQCHierarchyTable

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
}
