package Database.DataModel.Model.CQCElement

import Database.DataModel.DomainModel
import Database.Signature.Model.CQCElement.CQCElementModelSignature

import java.util.UUID

case class CQCElement(id: UUID,
                      parentId: UUID,
                      elemType: String,
                      value: String) extends CQCElementModelSignature with DomainModel {
  /**
   * Получением потомков Элемента ККХ
   *
   * @return последовательноть потомков Элемента ККХ
   */
    //todo: получение детей
  override def children: Seq[CQCElement] = ???

  /**
   * Получение родителя Элемента ККХ
   *
   * @return родителя
   */
    //todo: поулчение родителей
  override def parent: Option[CQCElement] = ???
}
