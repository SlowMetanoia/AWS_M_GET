package Database.Signature.Table

import Database.Signature.TableSignature

import java.util.UUID

trait CourseOutputLeafsTableSignature extends TableSignature {
  val courseId: UUID
  val leafId: UUID
}
