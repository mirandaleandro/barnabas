package models

import net.fwbrasil.activate.migration.Migration
import models.PostgresConnection._
import securesocialpersistence.{UserIdentity, UserAuthenticationMethod}
import xml.dtd.SystemID
import compat.Platform
import securesocial.core._
import securesocial.core.providers.utils
import utils.BCryptPasswordHasher
import com.jolbox.bonecp.UsernamePassword

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
         val socialUser = SocialUser(IdentityId("mirandaleandro@gmail.com","userpass"),
           "Azul",
           "Azul Negro Vermelho",
           "Vermelho",
           Some("mirandaleandro@gmail.com"),
           Some("http://www.gravatar.com/avatar/da4550c6fcd6d5d4f880755587b995ac?d=404"),
           AuthenticationMethod("userPassword"),
           None,
           None,
           Some(Registry.hashers.currentHasher.hash("socrates")))

         val user = User()
         val identity = UserIdentity(user,socialUser)


       }



    }
}
