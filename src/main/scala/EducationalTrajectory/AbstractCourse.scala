package EducationalTrajectory

import java.util.UUID

/**
 * Абстрактный класс для всех курсов
 * С этим будут проблемы!
 *
 * @tparam BoxType тип коробок. Делается так, чтобы он был специфичен для каждой иерархии.
 * @tparam LeaveType тип листьев
 */
abstract class AbstractCourse[BoxType,LeaveType](
                                          hierarchyParts: Map[ String, Set[ BoxType ] ],
                                          name: String,
                                          id: UUID,
                                          content:Map[String,_],
                                          inputs:Set[LeaveType],
                                          outputs:Set[LeaveType]
                                        )
