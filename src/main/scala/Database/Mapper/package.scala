package Database

import Database.Model.Entity.{CQCElementDictionaryEntity, CQCElementEntity, CQCElementHierarchyEntity, Entity}
import Database.Table.{CQCElementDictionaryTable, CQCElementHierarchyTable, CQCElementTable, Table}

package object Mapper {
  sealed trait EntityMapper[EntityType <: Entity, TableType <: Table] {
    def tableRow2Entity(row: TableType): EntityType

    def entity2TableRow(entity: EntityType): TableType
  }

  trait CQCElementEntityMapper extends EntityMapper[CQCElementEntity, CQCElementTable]
  trait CQCElementDictionaryEntityMapper extends EntityMapper[CQCElementDictionaryEntity, CQCElementDictionaryTable]
  trait CQCElementHierarchyEntityMapper extends EntityMapper[CQCElementHierarchyEntity, CQCElementHierarchyTable]
}
