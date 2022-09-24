package Database.Signature.EntityAndTable

import Database.Signature.TableSignature

import java.util.UUID

trait CourseTableSignature extends TableSignature {
  val id: UUID
  val name: String
}

trait CourseEntitySignature extends CourseTableSignature {
  val inputLeaf: Seq[CQCElementEntitySignature]
  val outputLeaf: Seq[CQCElementEntitySignature]
}