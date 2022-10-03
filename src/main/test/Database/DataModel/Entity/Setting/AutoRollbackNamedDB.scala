package Database.DataModel.Entity.Setting

import scalikejdbc.specs2.mutable.AutoRollback
import scalikejdbc.{DB, NamedDB}

trait AutoRollbackNamedDB extends AutoRollback {
  override def db(): DB = NamedDB("test").toDB()
}
