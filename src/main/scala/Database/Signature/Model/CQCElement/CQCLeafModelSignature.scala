package Database.Signature.Model.CQCElement

import java.util.UUID

trait CQCLeafModelSignature extends CQCHierarchyElementModelSignature {
  val id: UUID
  val parentId: UUID
  val elemType: String
  val value: String

  /**
   * Получение родителя Элемента ККХ
   *
   * @return родителя
   */
  def parent: Option[CQCElementModelSignature] = ???
}
