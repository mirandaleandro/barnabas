package models

import net.fwbrasil.activate.entity.Entity
import securesocial.core.providers.Token
import models.PostgresConnection._
import securesocial.core.providers.Token
import play.Logger

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/3/14
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */

class SecureSocialToken( var uuid:String,
                         var email: String,
                         var creationTime : org.joda.time.DateTime,
                         var expirationTime : org.joda.time.DateTime,
                         var isSignUp : scala.Boolean) extends Entity
object SecureSocialToken
{
  def apply(token:Token):SecureSocialToken =
  {
    transactional
    {
      new SecureSocialToken(token.uuid,token.email,token.creationTime,token.expirationTime, token.isSignUp)
    }
  }

  def unapply(token:SecureSocialToken):Token =
  {
    transactional{
      Token(token.uuid,token.email,token.creationTime,token.expirationTime, token.isSignUp)
    }
  }


  def findByUuid(uuid:String):Option[SecureSocialToken] =
  {
    transactional
    {
      (select[SecureSocialToken] where(_.uuid :== uuid)).headOption
    }
  }

  def tokenByUuid(uuid:String):Option[Token] =
  {
    findByUuid(uuid).map
    {
      ssToken =>
        SecureSocialToken.unapply(ssToken)
    }
  }

  def deleteToken(uuid: String) {
    transactional
    {
      findByUuid(uuid).map{_.delete}
    }
  }

  def deleteTokens() {
    transactional
    {
      all[SecureSocialToken].foreach{_.delete}
    }
  }

  def deleteExpiredTokens()
  {
    transactional
    {
      all[SecureSocialToken].foreach
      {
        ssToken:SecureSocialToken =>
          if ( SecureSocialToken.unapply(ssToken).isExpired)
            ssToken.delete

      }
    }
  }
}
