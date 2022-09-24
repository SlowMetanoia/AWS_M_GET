package Database.Signature.EntityAndTable

import Database.Signature.TableSignature

trait CQCHierarchyEntitySignature extends TableSignature {
  val childType: String
  val parentType: String
}
