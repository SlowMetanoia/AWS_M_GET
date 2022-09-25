package Database.DataModel

import scalikejdbc.ParameterBinderFactory

import java.util.UUID

package object Entity {
  trait UUIDFactory {
    implicit val uuidFactory: ParameterBinderFactory[UUID] = ParameterBinderFactory[UUID] {
      value => (stmt, idx) => stmt.setObject(idx, value)
    }
  }
}
