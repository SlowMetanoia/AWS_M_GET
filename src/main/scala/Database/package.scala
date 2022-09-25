import scalikejdbc.interpolation.SQLSyntax

package object Database {
  /**
   * "Импровизированное перечисление"
   * Его наличие обусловленно ограничениями работы ScalikeJDBC
   * и небезопаснотью механизма SQLSyntax.createUnsafely,
   * который подвержен SQL инъекциям
   * */
  sealed class SQLSyntaxProvider(val value: SQLSyntax)

  object SQLSyntaxProvider {
    def apply(str: String): SQLSyntax = SQLSyntax.createUnsafely(str)
  }

  /**
   * Названия колонок таблиц из БД
   * */
  object Id extends SQLSyntaxProvider(SQLSyntaxProvider("id"))

  object Name extends SQLSyntaxProvider(SQLSyntaxProvider("name"))

  /**
   * Операторы SQL
   * */
  object ASC extends SQLSyntaxProvider(SQLSyntaxProvider("ASC"))

  object DESC extends SQLSyntaxProvider(SQLSyntaxProvider("DESC"))

  val SQLOperatorsDictionary = Map(
    "asc" -> ASC.value,
    "desc" -> DESC.value,
  )

  val SQLOrderByDictionary = Map(
    "id" -> Id.value,
    "name" -> Name.value
  )
}
