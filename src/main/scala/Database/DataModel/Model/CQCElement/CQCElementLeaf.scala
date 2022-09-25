package Database.DataModel.Model.CQCElement

import Database.DataModel.DomainModel
import Database.Signature.Model.CQCElement.{CQCElementModelSignature, CQCLeafModelSignature}

import java.util.UUID

case class CQCElementLeaf(id: UUID,
                          parentId: UUID,
                          elemType: String,
                          value: String,
                       ) extends CQCLeafModelSignature with DomainModel {
  /**
   * Получение родителя Элемента ККХ
   *
   * @return родителя
   */
    //todo: получение родителей
  override def parent: Option[CQCElementModelSignature] = ???
}
