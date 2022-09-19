package EducationalTrajectory.KAS

abstract class PureKAS[T]{
  val value:T
  override def equals(obj: Any): Boolean = {
    case sameType:PureKAS[T] => sameType.value == this.value
    case _ => false
  }
}