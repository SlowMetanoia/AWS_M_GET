package Database.Table

import scalikejdbc._

case class CQCElementDictionaryTable(name: String) extends CQCElementDictionarySignature

object CQCElementDictionaryTable extends SQLSyntaxSupport[CQCElementDictionaryTable] {
  val cqcDict: QuerySQLSyntaxProvider[SQLSyntaxSupport[CQCElementDictionaryTable], CQCElementDictionaryTable] = CQCElementDictionaryTable.syntax("cqc_dict")
  val cqcDictC: ColumnName[CQCElementDictionaryTable] = CQCElementDictionaryTable.column

  override val schemaName: Some[String] = Some("courses")
  override val tableName = "cqc_elem_dict"

  def apply(r: ResultName[CQCElementDictionaryTable])(rs: WrappedResultSet): CQCElementDictionaryTable =
    new CQCElementDictionaryTable(
      name = rs.string(r.name)
    )
}
