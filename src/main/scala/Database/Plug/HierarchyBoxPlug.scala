package Database.Plug

import scalikejdbc._

import java.util.UUID

case class HierarchyBoxPlug(
                             id: UUID,
                             parentId: UUID,
                             elemType: String,
                           )

object HierarchyBoxPlug extends SQLSyntaxSupport[HierarchyBoxPlug] {
  val cqc: QuerySQLSyntaxProvider[SQLSyntaxSupport[HierarchyBoxPlug], HierarchyBoxPlug] = HierarchyBoxPlug.syntax("cqc")
  val cqcC: ColumnName[HierarchyBoxPlug] = HierarchyBoxPlug.column

  override val schemaName: Some[String] = Some("courses")
  override val tableName = "cqc_elements"

  def apply(r: ResultName[HierarchyBoxPlug])(rs: WrappedResultSet): HierarchyBoxPlug =
    new HierarchyBoxPlug(
      id = UUID.fromString(rs.get(r.id)),
      parentId = UUID.fromString(rs.get(r.parentId)),
      elemType = rs.string(r.elemType)
    )
}
