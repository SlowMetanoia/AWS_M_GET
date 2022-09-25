package Database.Signature.Table

import Database.Signature.TableSignature

import java.util.UUID

trait CourseTableSignature extends TableSignature {
  val id: UUID
  val name: String
}