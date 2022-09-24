package Database

import Database.Model.Entity.{CQCElementEntity, UUIDFactory}
import scalikejdbc.DBSession

package object Model {
  trait EntityModel
  trait TableModel
  trait DomainModel

  trait HierarchyEntityModel extends EntityModel with UUIDFactory {
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
}
