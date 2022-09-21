package Database.Model.Entity

import java.util.UUID

sealed trait Entity

//TODO
trait CQCElementEntityTrait extends Entity {
  val id: UUID
  val parentId: UUID
  val elemType: String
  val children: Seq[CQCElementEntityTrait]
}
