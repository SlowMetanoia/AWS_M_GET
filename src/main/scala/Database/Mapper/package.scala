package Database

import Database.Model.Entity.{CQCElementDictionaryEntity, CQCElementEntity, Entity}
import Database.Table.{CQCElementDictionaryTable, CQCElementTable, Table}

package object Mapper {
  sealed trait EntityMapper[EntityType <: Entity, TableType <: Table] {
    def tableRow2Entity(row: TableType): EntityType

    def entity2TableRow(entity: EntityType): TableType
  }

  trait CQCElementEntityMapper extends EntityMapper[CQCElementEntity, CQCElementTable]
  trait CQCElementDictionaryEntityMapper extends EntityMapper[CQCElementDictionaryEntity, CQCElementDictionaryTable]

}
