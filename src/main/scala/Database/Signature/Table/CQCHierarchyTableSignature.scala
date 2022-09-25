package Database.Signature.Table

import Database.Signature.TableSignature

trait CQCHierarchyTableSignature extends TableSignature {
  val childType: String
  val parentType: String
}
