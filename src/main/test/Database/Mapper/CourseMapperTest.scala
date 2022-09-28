package Database.Mapper

import Database.DataModel.Entity.{CQCElementEntity, CourseEntity}
import Database.DataModel.Model.CQCElement.CQCElementLeaf
import Database.DataModel.Model.Course
import Database.DataModel.Table.CourseTable
import Database.Signature.Model.CQCElement.CQCHierarchyElementModelSignature
import org.specs2.mutable.Specification

import java.util.UUID

object CourseMapperTest extends Specification {
  val relations: Map[String, Set[String]] = Map(
    "Компетенция" -> Set("Индикатор"),
    "Индикатор" -> Set("Знание", "Умение", "Навык")
  )
  val leafsEntity: Seq[CQCElementEntity] = Seq(
    CQCElementEntity(id = UUID.randomUUID(), parentId = UUID.randomUUID(), elemType = "Знание", value = "ВхЗнание1"),
    CQCElementEntity(id = UUID.randomUUID(), parentId = UUID.randomUUID(), elemType = "Знание", value = "ВхЗнание2")
  )
  val leafs: Seq[CQCElementLeaf] = leafsEntity.map(CQCElementMapper.entity2LeafModel)

  val partsEntity: Map[String, Seq[CQCElementEntity]] = Map(
    "Компетенция" -> Seq(CQCElementEntity(id = UUID.randomUUID(), parentId = null, elemType = "Компетенция", value = "Компетенция1")),
    "Индикатор" -> Seq(CQCElementEntity(id = UUID.randomUUID(), parentId = UUID.randomUUID(), elemType = "Индикатор", value = "Индикатор1")),
    "Знание" -> leafsEntity,
    "Умение" -> leafsEntity,
    "Навык" -> leafsEntity
  )

  val parts: Map[String, Seq[CQCHierarchyElementModelSignature]] = Map(
    "Компетенция" -> partsEntity("Компетенция").map(CQCElementMapper.entity2RootModel),
    "Индикатор" -> partsEntity("Индикатор").map(CQCElementMapper.entity2Model),
    "Знание" -> partsEntity("Знание").map(CQCElementMapper.entity2LeafModel),
    "Умение" -> partsEntity("Умение").map(CQCElementMapper.entity2LeafModel),
    "Навык" -> partsEntity("Навык").map(CQCElementMapper.entity2LeafModel),
  )

  val model: Course = Course(
    id = UUID.randomUUID(),
    name = "Курс1",
    inputLeaf = leafs,
    outputLeaf = leafs,
    parts = parts
  )

  val entity: CourseEntity = CourseEntity(
    id = model.id,
    name = model.name,
    inputLeaf = leafsEntity,
    outputLeaf = leafsEntity
  )

  "from table row to entity" in {
    CourseMapper.tableRow2Entity(CourseTable(
      id = entity.id,
      name = entity.name
    ),
      inputLeaf = leafsEntity,
      outputLeaf = leafsEntity) mustEqual entity
  }

  "from entity to table row" in {
    CourseMapper.entity2TableRow(entity) mustEqual CourseTable(
      id = entity.id,
      name = entity.name
    )
  }

  "from entity to model" in {
    CourseMapper.entity2Model(entity, partsEntity, relations) mustEqual model
  }

  "from model to entity" in {
    CourseMapper.model2Entity(model) mustEqual entity
  }
}
