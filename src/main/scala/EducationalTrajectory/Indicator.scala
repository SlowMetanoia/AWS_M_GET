package EducationalTrajectory

import EducationalTrajectory.KAS.KAS

import java.util.UUID

trait Indicator extends TableTemplate {
  val KASes:Set[KAS]
}
