package Database.Signature.Model

import Database.Signature.ModelSignature

import java.util.UUID

trait HierarchyComponentSignature extends ModelSignature {
  val id: UUID
  val elemType: String
  val value: String
}

trait HierarchyRootSignature extends HierarchyComponentSignature

trait HierarchyPartSignature extends HierarchyComponentSignature {
  val parentId: UUID
}

trait HierarchyLeafSignature extends HierarchyPartSignature


