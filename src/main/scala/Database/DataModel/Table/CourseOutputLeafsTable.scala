package Database.DataModel.Table

import Database.DataModel.TableModel
import Database.Signature.Table.CourseOutputLeafsTableSignature
import scalikejdbc._

import java.util.UUID

case class CourseOutputLeafsTable(courseId: UUID,
                                  leafId: UUID) extends CourseOutputLeafsTableSignature with TableModel

object CourseOutputLeafsTable extends SQLSyntaxSupport[CourseOutputLeafsTable] {
  val col: QuerySQLSyntaxProvider[SQLSyntaxSupport[CourseOutputLeafsTable], CourseOutputLeafsTable] = CourseOutputLeafsTable.syntax("c_o_l")
  val colC: ColumnName[CourseOutputLeafsTable] = CourseOutputLeafsTable.column

  override val schemaName: Some[String] = Some("cqc")
  override val tableName = "course_output_leaf_link"

  def apply(r: ResultName[CourseOutputLeafsTable])(rs: WrappedResultSet): CourseOutputLeafsTable =
    new CourseOutputLeafsTable(
      courseId = UUID.fromString(rs.get(r.courseId)),
      leafId = UUID.fromString(rs.get(r.leafId))
    )
}
