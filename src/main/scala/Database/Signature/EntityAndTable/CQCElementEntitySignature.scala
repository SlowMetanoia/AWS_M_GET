package Database.Signature.EntityAndTable

import Database.Signature.TableSignature

import java.util.UUID

trait CQCElementEntitySignature extends TableSignature {
  val id: UUID
  val parentId: UUID
  val elemType: String
  val value: String
}