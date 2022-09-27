package Database.Signature.Model.CQCElement

import java.util.UUID

trait CQCElementModelSignature extends CQCHierarchyElementModelSignature {
  val id: UUID
  val parentId: UUID
  val elemType: String
  val value: String

  /**
   * Получение родителя Элемента ККХ
   *
   * @return родителя
   */
  def parent(dbName: String): Option[CQCElementModelSignature]

  /**
   * Получением потомков Элемента ККХ
   *
   * @return последовательноть потомков Элемента ККХ
   */
  def children(dbName: String): Seq[CQCElementModelSignature]
}
