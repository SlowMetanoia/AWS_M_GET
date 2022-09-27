package Database.DataModel.Model

import Database.DataModel.DomainModel
import Database.DataModel.Model.CQCElement.CQCElementLeaf
import Database.Signature.Model.CQCElement.CQCHierarchyElementModelSignature
import Database.Signature.Model.CourseModelSignature

import java.util.UUID

case class Course(id: UUID,
                  name: String,
                  inputLeaf: Seq[CQCElementLeaf],
                  outputLeaf: Seq[CQCElementLeaf],
                  parts: Map[String, Seq[CQCHierarchyElementModelSignature]]) extends CourseModelSignature with DomainModel
