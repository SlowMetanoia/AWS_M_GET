package Database.Signature.Model

import Database.Signature.ModelSignature

trait CQCHierarchyModelSignature extends ModelSignature {
  val childType: String
  val parentType: String
}
