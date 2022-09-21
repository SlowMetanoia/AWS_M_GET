package AbstractHierarchy

import scala.annotation.tailrec

class RelationTable(getRelationTable:()=>Map[String,Option[String]]) {
  val relations:Map[String,Option[String]] = getRelationTable()
  val relationsChain: LazyList[String] = {
    val root = relations.find(pair => pair._2.isEmpty).getOrElse(throw new Exception("No root in hierarchy"))._1
    @tailrec
    def recollectChain( lList: LazyList[ String ] ): LazyList[ String ] = relations.find(p=> p._2.contains(lList.head)) match {
      case Some((child,_)) => recollectChain(child #:: lList)
      case _ => lList
    }
    recollectChain(LazyList(root)).reverse
  }
  override def toString: String = {
    s"${this.getClass.getSimpleName}${relationsChain.force.mkString("(",",",")")}"
  }
}
object RelationTable{
  val defaultInits: ( ) => Map[String, Option[String ] ] = ()=>{ Map(
    "0"->Some("1"),
    "3"->Some("4"),
    "2"->Some("3"),
    "1"->Some("2"),
    "4"->Some("5"),
    "5"->None
    )}
  def apply( getRelationTable: ( ) => Map[ String, Option[ String ] ] ): RelationTable = new RelationTable(getRelationTable)
  //todo: pass this to tests: println(RelationTable(defaultInits))
}
