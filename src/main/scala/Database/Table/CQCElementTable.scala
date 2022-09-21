package Database.Table

import scalikejdbc._

import java.util.UUID

/**
 * Таблица cqc - элемнты ККХ
 *
 * @param id       элемента ККХ
 * @param parentId id родителя данного элемента
 * @param elemType тип данного элемента
 */
case class CQCElementTable(id: UUID,
                           parentId: UUID,
                           elemType: String) extends Table

object CQCElementTable extends SQLSyntaxSupport[CQCElementTable] {
  val cqc: QuerySQLSyntaxProvider[SQLSyntaxSupport[CQCElementTable], CQCElementTable] = CQCElementTable.syntax("cqc")
  val cqcC: ColumnName[CQCElementTable] = CQCElementTable.column

  override val schemaName: Some[String] = Some("courses")
  override val tableName = "cqc_elements"

  def apply(r: ResultName[CQCElementTable])(rs: WrappedResultSet): CQCElementTable =
    new CQCElementTable(
      id = UUID.fromString(rs.get(r.id)),
      parentId = UUID.fromString(rs.get(r.parentId)),
      elemType = rs.string(r.elemType)
    )
}
