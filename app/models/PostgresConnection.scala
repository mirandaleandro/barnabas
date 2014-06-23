// CODE FOR HEROKU DEPLOYMENT
package models

import net.fwbrasil.activate.ActivateContext
import net.fwbrasil.activate.storage.relational.PooledJdbcRelationalStorage
import net.fwbrasil.activate.storage.relational.idiom.postgresqlDialect
import net.fwbrasil.activate.storage.memory.TransientMemoryStorage
import net.fwbrasil.activate.ActivateContext

object PostgresConnection extends ActivateContext
{
  val storage = new PooledJdbcRelationalStorage {
    val jdbcDriver = "org.postgresql.Driver"
    val user = "postgres"
    val password = "socrates"
    val url = "jdbc:postgresql://127.0.0.1/barnabas"
    val dialect = postgresqlDialect
  }
}