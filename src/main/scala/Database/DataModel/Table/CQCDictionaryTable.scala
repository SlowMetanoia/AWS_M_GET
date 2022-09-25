package Database.DataModel.Table

import Database.DataModel.TableModel
import Database.Signature.Table.CQCDictionaryTableSignature
import scalikejdbc._

case class CQCDictionaryTable(name: String) extends CQCDictionaryTableSignature with TableModel

object CQCDictionaryTable extends SQLSyntaxSupport[CQCDictionaryTable] {
  val cqcDict: QuerySQLSyntaxProvider[SQLSyntaxSupport[CQCDictionaryTable], CQCDictionaryTable] = CQCDictionaryTable.syntax("cqc_dict")
  val cqcDictC: ColumnName[CQCDictionaryTable] = CQCDictionaryTable.column

  override val schemaName: Some[String] = Some("cqc")
  override val tableName = "cqc_elem_dict"

  def apply(r: ResultName[CQCDictionaryTable])(rs: WrappedResultSet): CQCDictionaryTable =
    new CQCDictionaryTable(
      name = rs.string(r.name)
    )
}
