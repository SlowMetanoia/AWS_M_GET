package EducationalTrajectory

import AbstractHierarchy.HierarchyDescription

import java.util.UUID

class CourseFactory(courseStructureHierarchy: HierarchyDescription) {
  type hBox = courseStructureHierarchy.HierarchyBox.type
  case class ActualCourse(
                parts: Map[ String, Set[ hBox ] ],
                name: String,
                id: UUID,
                inputs:Set[hBox],
                outputs:Set[hBox]
                         ) {
    val leaveLevelName: String = courseStructureHierarchy.hierarchyRelationTable.relationsChain.head
    def relationsLeaves: Set[ hBox ] = parts(leaveLevelName)
  }
  
  object Course {
    val hierarchy: HierarchyDescription = courseStructureHierarchy
  }
}