package Database.Mapper

import Database.DataModel.Entity.{CQCElementEntity, CourseEntity}
import Database.DataModel.Model.CQCElement.{CQCElement, CQCElementLeaf, CQCElementRoot}
import Database.DataModel.Model.Course
import Database.Signature.Model.CQCElement.CQCHierarchyElementModelSignature
import org.specs2.mutable.Specification

import java.util.UUID

object CourseMapperTest extends Specification {
  val id: UUID = UUID.randomUUID()
  val parentId: UUID = UUID.randomUUID()

  val relations: Map[String, Set[String]] = Map(
    "Компетенция" -> Set("Индикатор"),
    "Индикатор" -> Set("Знание", "Умение", "Навык")
  )
  val inputLeafsEntity: Seq[CQCElementEntity] = Seq(
    CQCElementEntity(id = id, parentId = parentId, elemType = "Знание", value = "ВхЗнание1"),
    CQCElementEntity(id = id, parentId = parentId, elemType = "Знание", value = "ВхЗнание2")
  )
  val outputLeafsEntity: Seq[CQCElementEntity] = Seq(
    CQCElementEntity(id = id, parentId = parentId, elemType = "Умение", value = "ВыхЗнание1"),
    CQCElementEntity(id = id, parentId = parentId, elemType = "Умение", value = "ВыхЗнание2")
  )

  val inputLeafs: Seq[CQCElementLeaf] = Seq(
    CQCElementLeaf(id = id, parentId = parentId, elemType = "Знание", value = "ВхЗнание1"),
    CQCElementLeaf(id = id, parentId = parentId, elemType = "Знание", value = "ВхЗнание2")
  )

  val outputLeafs: Seq[CQCElementLeaf] = Seq(
    CQCElementLeaf(id = id, parentId = parentId, elemType = "Умение", value = "ВыхЗнание1"),
    CQCElementLeaf(id = id, parentId = parentId, elemType = "Умение", value = "ВыхЗнание2")
  )

  val parts: Map[String, Seq[CQCHierarchyElementModelSignature]] = Map(
    "Компетенция" -> Seq(CQCElementRoot(id = id, elemType = "Компетенция", value = "Компетенция1")),
    "Индикатор" -> Seq(CQCElement(id = parentId, parentId = parentId, elemType = "Индикатор", value = "Индикатор1")),
    "Знание" -> Seq(
      CQCElementLeaf(id = parentId, parentId = parentId, elemType = "Знание", value = "Знание1"),
      CQCElementLeaf(id = parentId, parentId = parentId, elemType = "Знание", value = "Знание2")),
    "Умение" -> Seq(
      CQCElementLeaf(id = parentId, parentId = parentId, elemType = "Умение", value = "Умение1"),
      CQCElementLeaf(id = parentId, parentId = parentId, elemType = "Умение", value = "Умение2")),
    "Навык" -> Seq(
      CQCElementLeaf(id = id, parentId = parentId, elemType = "Навык", value = "Навык1"),
      CQCElementLeaf(id = id, parentId = parentId, elemType = "Навык", value = "Навык2"),
    )
  )

  val partsEntity: Map[String, Seq[CQCElementEntity]] = Map(
    "Компетенция" -> Seq(CQCElementEntity(id = id, parentId = null, elemType = "Компетенция", value = "Компетенция1")),
    "Индикатор" -> Seq(CQCElementEntity(id = id, parentId = parentId, elemType = "Индикатор", value = "Индикатор1")),
    "Знание" -> Seq(
      CQCElementEntity(id =id, parentId = parentId, elemType = "Знание", value = "Знание1"),
      CQCElementEntity(id =id, parentId = parentId, elemType = "Знание", value = "Знание2")),
    "Умение" -> Seq(
      CQCElementEntity(id = id, parentId = parentId, elemType = "Умение", value = "Умение1"),
      CQCElementEntity(id = id, parentId = parentId, elemType = "Умение", value = "Умение2")),
    "Навык" -> Seq(
      CQCElementEntity(id = id, parentId = parentId, elemType = "Навык", value = "Навык1"),
      CQCElementEntity(id = id, parentId = parentId, elemType = "Навык", value = "Навык2"),
    )
  )

  val model: Course = Course(
    id = UUID.randomUUID(),
    name = "Курс1",
    inputLeaf = inputLeafs,
    outputLeaf = outputLeafs,
    parts = parts
  )

  "from entity to model" in {
    CourseMapper.entity2Model(CourseEntity(
      id = model.id,
      name = model.name,
      inputLeaf = inputLeafsEntity,
      outputLeaf = outputLeafsEntity,
    ),
      relations = relations,
      parts = partsEntity
    ) mustEqual model
  }

}
