package Database.Signature.EntityAndTable

import Database.Signature.TableSignature

import java.util.UUID

trait CourseOutputLeafsSignature extends TableSignature {
  val courseId: UUID
  val leafId: UUID
}
