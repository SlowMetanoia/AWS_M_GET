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

  override def entity2Model(entity: CourseEntity, parts: Map[String, CQCElementEntity]): Course =
    Course(
      id = entity.id,
      name = entity.name,
      inputLeaf = entity.inputLeaf.map(CQCElementMapper.entity2LeafModel),
      outputLeaf = entity.outputLeaf.map(CQCElementMapper.entity2LeafModel),
      parts = parts.map(pair => (pair._1, CQCElementMapper.entity2Model(pair._2)))
    )

  override def model2Entity(model: Course): CourseEntity = ???
}
