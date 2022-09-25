package Database.Signature.Entity

import Database.DataModel.Entity.CQCElementEntity
import Database.Signature.EntitySignature
import scalikejdbc.DBSession

import java.util.UUID

trait CQCElementEntitySignature extends EntitySignature {
  val id: UUID
  val parentId: UUID
  val elemType: String
  val value: String

  /**
   * Получение родителя Элемента ККХ
   *
   * @return родителя
   */
  def parent(implicit session: DBSession): Option[CQCElementEntity]

  /**
   * Получением потомков Элемента ККХ
   *
   * @return последовательноть потомков Элемента ККХ
   */
  def children(implicit session: DBSession): Seq[CQCElementEntity]
}