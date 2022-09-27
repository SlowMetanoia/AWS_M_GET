package Database.DataModel.Model.CQCElement

import Database.DataModel.DomainModel
import Database.Mapper.CQCElementMapper
import Database.Signature.Model.CQCElement.CQCElementModelSignature
import scalikejdbc.NamedDB

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
  override def children(dbName: String): Seq[CQCElement] =
    NamedDB(dbName) localTx { implicit session =>
      CQCElementMapper.model2Entity(this).children.map(CQCElementMapper.entity2Model)
    }

  /**
   * Получение родителя Элемента ККХ
   *
   * @return родителя
   */
  override def parent(dbName: String): Option[CQCElement] =
    NamedDB(dbName) localTx { implicit session =>
      CQCElementMapper.model2Entity(this).parent.map(CQCElementMapper.entity2Model)
    }
}
