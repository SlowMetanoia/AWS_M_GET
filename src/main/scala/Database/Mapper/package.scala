package Database

import Database.Model.Entity.{CQCDictionaryEntity, CQCElementEntity, CQCHierarchyEntity, CourseEntity}
import Database.Model.{EntityModel, TableModel}
import Database.Model.Table.{CQCDictionaryTable, CQCElementTable, CQCHierarchyTable, CourseTable}

package object Mapper {
  sealed trait EntityMapper[EntityType <: EntityModel, TableType <: TableModel] {
    def tableRow2Entity(row: TableType): EntityType

    def entity2TableRow(entity: EntityType): TableType
  }

  trait CQCElementEntityMapper extends EntityMapper[CQCElementEntity, CQCElementTable]
  trait CQCDictionaryEntityMapper extends EntityMapper[CQCDictionaryEntity, CQCDictionaryTable]
  trait CQCHierarchyEntityMapper extends EntityMapper[CQCHierarchyEntity, CQCHierarchyTable]
}
