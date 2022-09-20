package Database.Model.Entity

import Database.{ASC, Id}
import Database.Plug.HierarchyBoxPlug
import Database.Plug.HierarchyBoxPlug.cqc
import scalikejdbc._

import java.util.UUID

case class HierarchyBoxEntity(
                         id: UUID,
                         parentId: UUID,
                         elemType: String,
                         children: Seq[HierarchyBoxEntity]
                       )

object HierarchyBoxEntity extends Entity {

  def findAll(limit: Int = 100,
              offset: Int = 0,
              orderBy: SQLSyntax = Id.value,
              sort: SQLSyntax = ASC.value)
             (implicit session: DBSession): Seq[HierarchyBoxEntity] = {
    val boxPlugs: Seq[HierarchyBoxPlug] =
      withSQL {
        select.all(cqc).from(HierarchyBoxPlug as cqc)
          .orderBy(orderBy).append(sort)
          .limit(limit)
          .offset(offset)
      }.map(HierarchyBoxPlug(cqc.resultName)).collection.apply()
  }
}
