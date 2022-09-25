package Database.Signature.Table

import Database.Signature.TableSignature

import java.util.UUID

trait CourseInputLeafsTableSignature extends TableSignature {
  val courseId: UUID
  val leafId: UUID
}
