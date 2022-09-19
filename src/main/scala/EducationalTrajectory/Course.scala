package EducationalTrajectory

import java.util.UUID

trait Course {
  val id:UUID
  val name:String
  val description:String
  val competences:Set[Competence]
}
