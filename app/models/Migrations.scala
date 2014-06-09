package models

import net.fwbrasil.activate.migration.Migration
import models.PostgresConnection._
import xml.dtd.SystemID
import compat.Platform

/**
 * Created with IntelliJ IDEA.
 * Usuario: felipe
 * Date: 7/8/12
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */

class CreateSchema extends Migration {
    def timestamp = Platform.currentTime//201406011240l

    def up {
        removeAllEntitiesTables
            .ifExists
            .cascade

        createTableForAllEntities.ifNotExists
        createInexistentColumnsForAllEntities

    }
}

class CreateData extends Migration {
    def timestamp = Platform.currentTime//201406011240l

    def up {
       customScript
       {

         val user:User = User();


       }

    }
}
