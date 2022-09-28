package Database.Mapper

import Database.DataModel.Entity.CQCHierarchyEntity
import Database.DataModel.Model.CQCHierarchy
import Database.DataModel.Table.CQCHierarchyTable
import org.specs2.mutable.Specification

object CQCHierarchyMapperTest extends Specification {
  val entity: CQCHierarchyEntity = CQCHierarchyEntity(
    childType = "Индикатор",
    parentType = "Компетенция"
  )

  "from tableRow to entity" in {
    CQCHierarchyMapper.tableRow2Entity(
      CQCHierarchyTable(
        childType = "Индикатор",
        parentType = "Компетенция"
      )
    ) mustEqual entity
  }

  "from entity to table row" in {
    CQCHierarchyMapper.entity2TableRow(entity) mustEqual CQCHierarchyTable(
      childType = "Индикатор",
      parentType = "Компетенция"
    )
  }

  "from entity to model" in {
    CQCHierarchyMapper.entity2Model(entity) mustEqual CQCHierarchy(
      childType = "Индикатор",
      parentType = "Компетенция"
    )
  }

  "from model to entity" in {
    CQCHierarchyMapper.model2Entity(CQCHierarchy(
      childType = "Индикатор",
      parentType = "Компетенция"
    )) mustEqual entity
  }
}
