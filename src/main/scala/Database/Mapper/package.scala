package Database

import Database.DataModel.Model.CQCElement.{CQCElement, CQCElementLeaf, CQCElementRoot}
import Database.DataModel.Model.{CQCDictionary, CQCHierarchy, Course}
import Database.DataModel.Entity.{CQCDictionaryEntity, CQCElementEntity, CQCHierarchyEntity, CourseEntity}
import Database.DataModel.Table.{CQCDictionaryTable, CQCElementTable, CQCHierarchyTable, CourseTable}
import Database.DataModel.{DomainModel, EntityModel, TableModel}
import Database.Signature.Entity.CQCElementEntitySignature

package object Mapper {
  sealed trait DirtyDataMapper[EntityType <: EntityModel, TableType <: TableModel, ModelType <: DomainModel]

  sealed trait ClearDataMapper[EntityType <: EntityModel, TableType <: TableModel, ModelType <: DomainModel] {
    def tableRow2Entity(row: TableType): EntityType

    def entity2TableRow(entity: EntityType): TableType

    def entity2Model(entity: EntityType): ModelType

    def model2Entity(model: ModelType): EntityType
  }

  trait CQCElementDataMapper extends ClearDataMapper[CQCElementEntity, CQCElementTable, CQCElement] {
    def entity2RootModel(entity: CQCElementEntity): CQCElementRoot

    def entity2LeafModel(entity: CQCElementEntity): CQCElementLeaf
  }

  trait CQCDictionaryDataMapper extends ClearDataMapper[CQCDictionaryEntity, CQCDictionaryTable, CQCDictionary]

  trait CQCHierarchyDataMapper extends ClearDataMapper[CQCHierarchyEntity, CQCHierarchyTable, CQCHierarchy]

  trait CourseDataMapper extends DirtyDataMapper[CourseEntity, CourseTable, Course] {
    def tableRow2Entity(row: CourseTable,
                        inputLeaf: Seq[CQCElementEntity],
                        outputLeaf: Seq[CQCElementEntity]): CourseEntity

    def entity2TableRow(entity: CourseEntity): CourseTable

    def entity2Model(entity: CourseEntity, parts: Map[String, CQCElementEntity]): Course

    def model2Entity(model: Course): CourseEntity
  }
}