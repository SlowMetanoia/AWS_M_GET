package Database.Signature.Model

import Database.Signature.ModelSignature

import java.util.UUID

trait CourseModelSignature extends ModelSignature {
  val id: UUID
  val name: String
  val inputLeaf: Seq[HierarchyPartSignature]
  val outputLeaf: Seq[HierarchyPartSignature]
  val parts: Map[String, HierarchyComponentSignature]
}
