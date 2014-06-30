// CODE FOR HEROKU DEPLOYMENT
package models

import net.fwbrasil.activate.storage.relational.PooledJdbcRelationalStorage
import net.fwbrasil.activate.storage.relational.idiom.postgresqlDialect
import net.fwbrasil.activate.ActivateContext

object PostgresConnection extends ActivateContext
{

  val storage = new PooledJdbcRelationalStorage {
    val jdbcDriver = "org.postgresql.Driver"
    val user = "kavafnpqoymcry"
    val password = "FZOKl0ExcsqMgCRkxbyWK1_CHR"
    val url = "jdbc:postgresql://ec2-54-204-21-178.compute-1.amazonaws.com/dbc4t576sli53u"
    val dialect = postgresqlDialect
  }
}
