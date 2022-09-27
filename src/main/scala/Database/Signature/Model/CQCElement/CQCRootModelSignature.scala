package Database.Signature.Model.CQCElement

import java.util.UUID

trait CQCRootModelSignature extends CQCHierarchyElementModelSignature {
  val id: UUID
  val elemType: String
  val value: String

  /**
   * Получением потомков Элемента ККХ
   *
   * @return последовательноть потомков Элемента ККХ
   */
  def children(dbName: String): Seq[CQCElementModelSignature]
}
