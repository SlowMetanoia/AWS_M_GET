package Database

import java.util.UUID

package object Table {
  sealed trait Table

  trait CQCElementTableSignature extends Table {
    val id: UUID
    val parentId: UUID
    val elemType: String
    val value: String
  }

  trait CQCElementDictionarySignature extends Table {
    val name: String
  }


}
