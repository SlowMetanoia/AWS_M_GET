package Database.Signature.Table

import Database.Signature.TableSignature

import java.util.UUID

trait CQCElementTableSignature extends TableSignature {
  val id: UUID
  val parentId: UUID
  val elemType: String
  val value: String
}