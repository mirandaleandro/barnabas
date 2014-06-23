// CODE FOR HEROKU DEPLOYMENT
package models

import net.fwbrasil.activate.ActivateContext
import net.fwbrasil.activate.storage.relational.PooledJdbcRelationalStorage
import net.fwbrasil.activate.storage.relational.idiom.postgresqlDialect
import net.fwbrasil.activate.storage.memory.TransientMemoryStorage
import net.fwbrasil.activate.ActivateContext

object PostgresConnection extends ActivateContext
{
 // override val storage = new TransientMemoryStorage

  val storage = new PooledJdbcRelationalStorage {
    val jdbcDriver = "org.postgresql.Driver"
    val user = "kavafnpqoymcry"
    val password = "FZOKl0ExcsqMgCRkxbyWK1_CHR"
    val url = "postgres://kavafnpqoymcry:FZOKl0ExcsqMgCRkxbyWK1_CHR@ec2-54-204-21-178.compute-1.amazonaws.com:5432/dbc4t576sli53u"
    val dialect = postgresqlDialect
  }
}

/*
HEROKU

Host	ec2-54-204-21-178.compute-1.amazonaws.com
Database	dbc4t576sli53u
User	kavafnpqoymcry
Port	5432
Password	Hide FZOKl0ExcsqMgCRkxbyWK1_CHR
Psql	 heroku pg:psql --app heroku-postgres-e45eb3c0 rose
URL	Hide postgres://kavafnpqoymcry:FZOKl0ExcsqMgCRkxbyWK1_CHR@ec2-54-204-21-178.compute-1.amazonaws.com:5432/dbc4t576sli53u


*/
