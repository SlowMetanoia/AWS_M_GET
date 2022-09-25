package Database

import Database.DataModel.Domain.CQCElement.CQCPartModel
import Database.DataModel.Domain.{CQCDictionaryModel, CQCHierarchyModel}
import Database.DataModel.Entity.{CQCDictionaryEntity, CQCElementEntity, CQCHierarchyEntity}
import Database.DataModel.Table.{CQCDictionaryTable, CQCElementTable, CQCHierarchyTable}
import Database.DataModel.{DomainModel, EntityModel, TableModel}

package object Mapper {
  sealed trait EntityMapper[EntityType <: EntityModel, TableType <: TableModel, ModelType <: DomainModel] {
    def tableRow2Entity(row: TableType): EntityType

    def entity2TableRow(entity: EntityType): TableType

    def entity2Model(entity: EntityType): ModelType

    def model2Entity(model: ModelType): EntityType
  }

  trait CQCElementEntityMapper extends EntityMapper[CQCElementEntity, CQCElementTable, CQCPartModel]
  trait CQCDictionaryEntityMapper extends EntityMapper[CQCDictionaryEntity, CQCDictionaryTable, CQCDictionaryModel]
  trait CQCHierarchyEntityMapper extends EntityMapper[CQCHierarchyEntity, CQCHierarchyTable, CQCHierarchyModel]
}
