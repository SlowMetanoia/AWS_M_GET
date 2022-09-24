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
  
    def apply( id: UUID, value: String, parentId: UUID, elemType: String, children: Seq[ HierarchyBox ]): HierarchyBox =
      new HierarchyBox(id, value, parentId, elemType, children)
      
    def unapply( arg: HierarchyBox ): Option[ (UUID, String, UUID, String, Seq[ HierarchyBox ]) ] = arg match {
      case obj:HierarchyBox if obj != null => Some((arg.id,arg.value,arg.parentId,arg.elemType,arg.children))
      case _ => None
    }
  }
  case class HierarchyLeave( override val id: UUID,
                             override val value: String,
                             override val parentId: UUID,
                             override val elemType: String,
                             override val children: Seq[HierarchyBox]
                           ) extends HierarchyBox(id, value, parentId, elemType, children)
  object HierarchyLeave{
    def fromHierarchyBox:HierarchyBox=>HierarchyLeave = {
      case HierarchyBox(id, value, parentId, elemType, children) =>
        HierarchyLeave(id, value, parentId, elemType, children)
    }
  }
}
object HierarchyDescription{
  val defaultInit: RelationTable = RelationTable(RelationTable.defaultInits)
  def apply( hierarchyRelationTable: RelationTable ): HierarchyDescription =
    new HierarchyDescription(hierarchyRelationTable)
  //todo: pass this to test:
  // println(HierarchyDescription(RelationTable(RelationTable.defaultInits)).HierarchyBox.hierarchyDescription)
}
