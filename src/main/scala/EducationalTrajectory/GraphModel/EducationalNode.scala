package EducationalTrajectory.GraphModel

/**
 *
 * @tparam T1 - тип хранимого значения
 *
 * @tparam T2 - тип значения на выходах
 */
case class EducationalNode[ T1, T2 ](override val value: Either[ T1, T2 ], override val outputs: Node[Either[ T1, T2 ]])
  extends Node[ Either[ T1, T2 ] ](value, outputs)
  
  