package Database

import Database.Model.Entity.{CQCElementEntity, Entity}
import Database.Table.{CQCElementTable, Table}

package object Mapper {
  sealed trait Mapper[EntityType <: Entity, TableType <: Table] {
    def tableRow2Entity(row: TableType): EntityType

    def entity2TableRow(entity: EntityType): TableType
  }

  trait CQCElementMapper extends Mapper[CQCElementEntity, CQCElementTable]

}
