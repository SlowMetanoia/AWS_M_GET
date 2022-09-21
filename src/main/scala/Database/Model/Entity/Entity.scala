package Database.Model.Entity

import java.util.UUID

/**
 * Маркерный трейт
 */
sealed trait Entity

trait CQCElementEntitySignature extends Entity {
  val id: UUID
  val parentId: UUID
  val elemType: String
  val value: String
}

trait CQCElementDictionaryEntitySignature extends Entity {
  val name: String
}

trait CQCElementHierarchyEntitySignature extends Entity {
  val childType: String
  val parentType: String
}
