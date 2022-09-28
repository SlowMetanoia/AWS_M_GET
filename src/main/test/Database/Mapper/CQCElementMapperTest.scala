package Database.Mapper

import Database.DataModel.Entity.CQCElementEntity
import Database.DataModel.Model.CQCElement.{CQCElement, CQCElementLeaf, CQCElementRoot}
import Database.DataModel.Table.CQCElementTable
import org.specs2.mutable.Specification

import java.util.UUID

object CQCElementMapperTest extends Specification {
  val entity: CQCElementEntity = CQCElementEntity(
    id = UUID.randomUUID(),
    parentId = UUID.randomUUID(),
    elemType = "Знание",
    value = "Знание №1"
  )

  val entityRoot: CQCElementEntity = CQCElementEntity(
    id = entity.id,
    parentId = null,
    elemType = "Знание",
    value = "Знание №1"
  )

  "table row to entity" in {
    CQCElementMapper.tableRow2Entity(CQCElementTable(
      id = entity.id,
      parentId = entity.parentId,
      elemType = entity.elemType,
      value = entity.value
    )) mustEqual entity
  }

  "entity to table row" in {
    CQCElementMapper.entity2TableRow(entity) mustEqual CQCElementTable(
      id = entity.id,
      parentId = entity.parentId,
      elemType = entity.elemType,
      value = entity.value
    )
  }

  "entity to model" in {
    CQCElementMapper.entity2Model(entity) mustEqual CQCElement(
      id = entity.id,
      parentId = entity.parentId,
      elemType = entity.elemType,
      value = entity.value
    )
  }

  "model to entity" in {
    CQCElementMapper.model2Entity(CQCElement(
      id = entity.id,
      parentId = entity.parentId,
      elemType = entity.elemType,
      value = entity.value
    )) mustEqual entity
  }

  "entity to root model" in {
    CQCElementMapper.entity2RootModel(entity) mustEqual CQCElementRoot(
      id = entity.id,
      elemType = entity.elemType,
      value = entity.value
    )
  }

  "root model to entity" in {
    CQCElementMapper.rootModel2Entity(CQCElementRoot(
      id = entity.id,
      elemType = entity.elemType,
      value = entity.value
    )) mustEqual entityRoot
  }

  "entity to leaf model" in {
    CQCElementMapper.entity2LeafModel(entity) mustEqual CQCElementLeaf(
      id = entity.id,
      parentId = entity.parentId,
      elemType = entity.elemType,
      value = entity.value
    )
  }

  "leaf model to entity" in {
    CQCElementMapper.leafModel2Entity(CQCElementLeaf(
      id = entity.id,
      parentId = entity.parentId,
      elemType = entity.elemType,
      value = entity.value
    )) mustEqual entity
  }
}