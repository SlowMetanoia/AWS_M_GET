package AbstractHierarchy

import java.util.UUID

class HierarchyDescription(
                            val hierarchyRelationTable: RelationTable
                          ) {
  
  class HierarchyBox(
                           val id: UUID,
                           val value: String,
                           val parentId: UUID,
                           val elemType: String,
                           val children: Seq[HierarchyBox]
                         )
  object HierarchyBox{
    val hierarchyDescription: HierarchyDescription = HierarchyDescription.this
  }
  case class HierarchyLeave( override val id: UUID,
                             override val value: String,
                             override val parentId: UUID,
                             override val elemType: String,
                             override val children: Seq[HierarchyBox]
                           ) extends HierarchyBox(id, value, parentId, elemType, children)
}
object HierarchyDescription extends App{
  def apply( hierarchyRelationTable: RelationTable ): HierarchyDescription = new HierarchyDescription(hierarchyRelationTable)
  HierarchyDescription(RelationTable(RelationTable.defaultInits))
}
