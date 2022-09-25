package Database.DataModel.Table

import Database.DataModel.TableModel
import Database.Signature.EntityAndTable.CourseTableSignature
import scalikejdbc._

import java.util.UUID

/**
 * Таблица course - курсы
 *
 * @param id   курса
 * @param name курса
 */
case class CourseTable(id: UUID,
                       name: String) extends CourseTableSignature with TableModel

object CourseTable extends SQLSyntaxSupport[CourseTable] {
  val c: QuerySQLSyntaxProvider[SQLSyntaxSupport[CourseTable], CourseTable] = CourseTable.syntax("c")
  val cC: ColumnName[CourseTable] = CourseTable.column

  override val schemaName: Some[String] = Some("cqc")
  override val tableName = "course"

  def apply(r: ResultName[CourseTable])(rs: WrappedResultSet): CourseTable =
    new CourseTable(
      id = UUID.fromString(rs.get(r.id)),
      name = rs.string(r.name)
    )
}
