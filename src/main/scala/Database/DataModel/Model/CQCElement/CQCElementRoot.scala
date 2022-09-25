package Database.DataModel.Model.CQCElement

import Database.DataModel.DomainModel
import Database.Signature.Model.CQCElement.{CQCElementModelSignature, CQCRootModelSignature}

import java.util.UUID

case class CQCElementRoot(id: UUID,
                          elemType: String,
                          value: String) extends CQCRootModelSignature with DomainModel {
  /**
   * Получением потомков Элемента ККХ
   *
   * @return последовательноть потомков Элемента ККХ
   */
    //todo: получение детей
  override def children: Seq[CQCElementModelSignature] = ???
}
