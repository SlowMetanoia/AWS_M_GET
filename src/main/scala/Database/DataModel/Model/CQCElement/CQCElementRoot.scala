package Database.DataModel.Model.CQCElement

import Database.DataModel.DomainModel
import Database.Mapper.CQCElementMapper
import Database.Signature.Model.CQCElement.{CQCElementModelSignature, CQCRootModelSignature}
import scalikejdbc.NamedDB

import java.util.UUID

case class CQCElementRoot(id: UUID,
                          elemType: String,
                          value: String) extends CQCRootModelSignature with DomainModel {
  /**
   * Получением потомков Элемента ККХ
   *
   * @return последовательноть потомков Элемента ККХ
   */
  override def children(dbName: String): Seq[CQCElementModelSignature] =
    NamedDB(dbName) localTx { implicit session =>
      CQCElementMapper.rootModel2Entity(this).children.map(CQCElementMapper.entity2Model)
    }
}
