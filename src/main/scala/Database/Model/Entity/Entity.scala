package Database.Model.Entity

import scalikejdbc.ParameterBinderFactory

import java.util.UUID

trait Entity {
  implicit val uuidFactory: ParameterBinderFactory[UUID] = ParameterBinderFactory[UUID] {
    value => (stmt, idx) => stmt.setObject(idx, value) }
}
