package Database.DataModel.Table

import Database.DataModel.TableModel
import Database.Signature.EntityAndTable.CQCHierarchyEntitySignature
import scalikejdbc._

/**
 * Таблица cqc_elem_hierarchy - иерархия элемеентов ККХ
 *
 * @param childType  тип потомка
 * @param parentType тип родителя
 */
case class CQCHierarchyTable(childType: String,
                             parentType: String) extends CQCHierarchyEntitySignature with TableModel

object CQCHierarchyTable extends SQLSyntaxSupport[CQCHierarchyTable] {
  val cqcHier: QuerySQLSyntaxProvider[SQLSyntaxSupport[CQCHierarchyTable], CQCHierarchyTable] = CQCHierarchyTable.syntax("cqcElHier")
  val cqcHierC: ColumnName[CQCHierarchyTable] = CQCHierarchyTable.column

  override val schemaName: Some[String] = Some("cqc")
  override val tableName = "cqc_elem_hierarchy"

  def apply(r: ResultName[CQCHierarchyTable])(rs: WrappedResultSet): CQCHierarchyTable =
    new CQCHierarchyTable(
      childType = rs.string(r.childType),
      parentType = rs.string(r.parentType),
    )
}