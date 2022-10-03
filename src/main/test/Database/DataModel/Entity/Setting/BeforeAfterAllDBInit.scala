package Database.DataModel.Entity.Setting

import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import scalikejdbc.config.DBs

abstract class BeforeAfterAllDBInit extends Specification with BeforeAfterAll {
  val DBName = "test"

  override def beforeAll(): Unit = {
    DBs.setupAll()
  }

  override def afterAll(): Unit = {
    DBs.closeAll()
  }
}
