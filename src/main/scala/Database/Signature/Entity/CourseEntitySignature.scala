package Database.Signature.Entity

import Database.DataModel.Entity.CQCElementEntity
import Database.Signature.EntitySignature
import scalikejdbc.DBSession

import java.util.UUID

trait CourseEntitySignature extends EntitySignature {
  val id: UUID
  val name: String
  val inputLeaf: Seq[CQCElementEntitySignature]
  val outputLeaf: Seq[CQCElementEntitySignature]

  /**
   * Получение элементов ККХ связанных с данным курсом
   *
   * @return последовательность элементов ККХ
   */
  def parts(implicit session: DBSession): Map[String, Seq[CQCElementEntity]]
}