package Database.DataModel.Table

import Database.DataModel.TableModel
import Database.Signature.EntityAndTable.CourseInputLeafsSignature
import scalikejdbc._

import java.util.UUID

case class CourseInputLeafsTable(courseId: UUID,
                                 leafId: UUID) extends CourseInputLeafsSignature with TableModel

object CourseInputLeafsTable extends SQLSyntaxSupport[CourseInputLeafsTable] {
  val cil: QuerySQLSyntaxProvider[SQLSyntaxSupport[CourseInputLeafsTable], CourseInputLeafsTable] = CourseInputLeafsTable.syntax("c_i_l")
  val cilC: ColumnName[CourseInputLeafsTable] = CourseInputLeafsTable.column

  override val schemaName: Some[String] = Some("cqc")
  override val tableName = "course_input_leaf_link"

  def apply(r: ResultName[CourseInputLeafsTable])(rs: WrappedResultSet): CourseInputLeafsTable =
    new CourseInputLeafsTable(
      courseId = UUID.fromString(rs.get(r.courseId)),
      leafId = UUID.fromString(rs.get(r.leafId))
    )
}
