package Database.DataModel.Entity

import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import scalikejdbc.NamedDB
import scalikejdbc.config.DBs

abstract class BeforeAfterAllDBInit extends Specification with BeforeAfterAll {
  val DBName = "test"



  override def beforeAll(): Unit = {
    DBs.setupAll()
    DBs.setup(dbName = DBName)
  }

  override def afterAll(): Unit = {
    DBs.closeAll()
  }
}
