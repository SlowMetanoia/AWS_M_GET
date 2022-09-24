package EducationalTrajectory

import AbstractHierarchy.HierarchyDescription

import java.util.UUID

/**
 *
 * @param courseStructureHierarchy Иерархия сущностей, описывающих курс (Компетенция->Индикатор->ЗУН)
 *
 * @param containedHierarchyParts Содержащиеся в объекте курса части иерархии
 *
 * @param courseContent Содержащиеся в объекте курса поля, описывающие сам курс. (Трудоёмкость и др.)
 */
class CourseFactory(
                     val courseStructureHierarchy: HierarchyDescription,
                     val containedHierarchyParts:Set[String],
                     val courseContent:Set[String]
                   ) {
  type HBox = courseStructureHierarchy.HierarchyBox
  type HLeave = courseStructureHierarchy.HierarchyLeave
  
  /** Объект, описывающий учебный курс.
   *
   * @param hierarchyParts Содержащиеся в объекте курса части иерархии
   * @param name Название курса
   * @param id uuid курса
   * @param content поля курса, описывающие сам курс
   * @param inputs Входы курса
   * @param outputs Выходы курса
   */
  case class ActualCourse(
                           hierarchyParts: Map[ String, Set[ HBox ] ],
                           name: String,
                           id: UUID,
                           content:Map[String,_],
                           inputs:Set[HLeave],
                           outputs:Set[HLeave]
                         )
    extends AbstractCourse[HBox,HLeave](
      hierarchyParts: Map[ String, Set[ HBox ] ],
      name: String,
      id: UUID,
      content:Map[String,_],
      inputs:Set[HLeave],
      outputs:Set[HLeave]
    ) {
    val leaveLevelName: String = courseStructureHierarchy.hierarchyRelationTable.relationsChain.head
    def relationLeaves: Set[ HLeave ] = hierarchyParts(leaveLevelName)
      .map(courseStructureHierarchy.HierarchyLeave.fromHierarchyBox)
  }
  
  object Course {
    val hierarchy: HierarchyDescription = courseStructureHierarchy
  }
}