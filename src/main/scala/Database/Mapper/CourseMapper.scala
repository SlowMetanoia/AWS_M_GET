package Database.Mapper

import Database.DataModel.Entity.{CQCElementEntity, CourseEntity}
import Database.DataModel.Model.Course
import Database.DataModel.Table.CourseTable

object CourseMapper extends CourseDataMapper {
  override def tableRow2Entity(row: CourseTable,
                               inputLeaf: Seq[CQCElementEntity],
                               outputLeaf: Seq[CQCElementEntity]): CourseEntity =
    CourseEntity(
      id = row.id,
      name = row.name,
      inputLeaf = inputLeaf,
      outputLeaf = outputLeaf
    )

  override def entity2TableRow(entity: CourseEntity): CourseTable =
    CourseTable(
      id = entity.id,
      name = entity.name
    )

  override def entity2Model(entity: CourseEntity,
                            parts: Map[String, Seq[CQCElementEntity]],
                            relations: Map[String, Set[String]]): Course = {
    //todo:переписать Exception нормально
    entity match {
      case CourseEntity(id,name,inputLeaf,outputLeaf) =>
    }
    val root = relations
      .keySet
      .find(rv=> !relations
        .values
        .flatten
        .exists(_==rv)
            ).get
    val leaves = relations.keySet -- relations.values.flatten.toSet
    Course(
      id = entity.id,
      name = entity.name,
      inputLeaf = entity.inputLeaf.map(CQCElementMapper.entity2LeafModel),
      outputLeaf = entity.outputLeaf.map(CQCElementMapper.entity2LeafModel),
      //todo: Вычислить все типы листов однажды и вынести в переменную
      parts = parts.map {
        case (k, v) if leaves.contains(k) => (k, v.map(CQCElementMapper.entity2LeafModel))
        case (k, v) if k==root => (k, v.map(CQCElementMapper.entity2RootModel))
        case (k, v) => (k, v.map(CQCElementMapper.entity2Model))
      }
    )
  }

  override def model2Entity(model: Course): CourseEntity =
    CourseEntity(
      id = model.id,
      name = model.name,
      inputLeaf = model.inputLeaf.map(CQCElementMapper.leafModel2Entity),
      outputLeaf = model.outputLeaf.map(CQCElementMapper.leafModel2Entity)
    )
}
