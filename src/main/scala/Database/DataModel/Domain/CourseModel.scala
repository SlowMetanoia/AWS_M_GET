package Database.DataModel.Domain

import Database.DataModel.Domain.CQCElement.{CQCLeafModel, CQCPartModel}
import Database.DataModel.DomainModel
import Database.Signature.Model.CourseModelSignature

import java.util.UUID

case class CourseModel(id: UUID,
                       name: String,
                       inputLeaf: Seq[CQCLeafModel],
                       outputLeaf: Seq[CQCLeafModel],
                       parts: Map[String, CQCPartModel]) extends CourseModelSignature with DomainModel

