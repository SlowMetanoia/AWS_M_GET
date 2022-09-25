package Database.Signature.Entity

import Database.Signature.EntitySignature

trait CQCHierarchyEntitySignature extends EntitySignature {
  val childType: String
  val parentType: String
}
