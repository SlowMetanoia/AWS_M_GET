package EducationalTrajectory.GraphModel

abstract class Node[T](val value:T,val outputs:Node[T])
trait node