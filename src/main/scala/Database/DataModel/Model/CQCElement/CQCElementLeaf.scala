package Database.DataModel.Model.CQCElement

import Database.DataModel.DomainModel
import Database.Mapper.CQCElementMapper
import Database.Signature.Model.CQCElement.{CQCElementModelSignature, CQCLeafModelSignature}
import scalikejdbc.NamedDB

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
  override def parent(dbName: String): Option[CQCElementModelSignature] = {
    NamedDB(dbName) localTx { implicit session =>
      CQCElementMapper.leafModel2Entity(this).parent.map(CQCElementMapper.entity2Model)
    }
  }
}
