package models

import net.fwbrasil.activate.migration.Migration
import models.PostgresConnection._

/**
 * Created with IntelliJ IDEA.
 * Usuario: felipe
 * Date: 7/8/12
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */

class CreateSchema extends Migration {
    def timestamp = 201406011240l

    def up {
        removeAllEntitiesTables
            .ifExists
            .cascade

        createTableForAllEntities.ifNotExists
        createInexistentColumnsForAllEntities

    }
}
