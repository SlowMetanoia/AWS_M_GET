package Database.Signature.Model

import Database.DataModel.Model.CQCElement.{CQCElement, CQCElementLeaf}
import Database.Signature.ModelSignature

import java.util.UUID

trait CourseModelSignature extends ModelSignature {
  val id: UUID
  val name: String
  val inputLeaf: Seq[CQCElementLeaf]
  val outputLeaf: Seq[CQCElementLeaf]
  val parts: Map[String, CQCElement]
}
