package Database.Table

import scalikejdbc._

/**
 * Таблица cqc_elem_hierarchy - иерархия элемеентов ККХ
 *
 * @param childType  тип потомка
 * @param parentType тип родителя
 */
case class CQCElementHierarchyTable(childType: String,
                                    parentType: String) extends CQCElementHierarchySignature

object CQCElementHierarchyTable extends SQLSyntaxSupport[CQCElementHierarchyTable] {
  val cqcHier: QuerySQLSyntaxProvider[SQLSyntaxSupport[CQCElementHierarchyTable], CQCElementHierarchyTable] = CQCElementHierarchyTable.syntax("cqcElHier")
  val cqcHierC: ColumnName[CQCElementHierarchyTable] = CQCElementHierarchyTable.column

  override val schemaName: Some[String] = Some("courses")
  override val tableName = "cqc_elem_hierarchy"

  def apply(r: ResultName[CQCElementHierarchyTable])(rs: WrappedResultSet): CQCElementHierarchyTable =
    new CQCElementHierarchyTable(
      childType = rs.string(r.childType),
      parentType = rs.string(r.parentType),
    )
}