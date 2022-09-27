import Database.DataModel.Model.CQCHierarchy

val entities: Set[CQCHierarchy] = Set(
  CQCHierarchy("A", "B"),
  CQCHierarchy("C", "A"),
  CQCHierarchy("D", "A"),
  CQCHierarchy("E", "A")
)
println(entities)
println(entities.map(row => (row.parentType, row.childType)))
val res = entities.map(row => (row.parentType, row.childType)).toMap
println(res)

val res = Map.from(entities.map(row => (row.parentType, row.childType)))

val mp = entities.groupBy(_.parentType).map {
  case (parent, childSet) => parent -> childSet.map(_.childType)
}
val mp2 = entities.groupMap(_.parentType)(_.childType)

/*
entities.foreach {
  case CQCHierarchy(childType, parentType) =>
    mp = mp + (parentType -> mp.getOrElse(parentType, Seq()).appended(childType))
  //mp = mp + (parentType->(mp(parentType).appended( childType)))
}*/
mp