package models.securesocialpersistence

import models.PostgresConnection._
import securesocial.core.IdentityId

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/5/14
 * Time: 1:12 AM
 * To change this template use File | Settings | File Templates.
 */

class UserIdentityId(val userId : scala.Predef.String, val providerId : scala.Predef.String) extends Entity

object UserIdentityId
{
  def apply(identityId:IdentityId): UserIdentityId =
  {
    transactional{
      new UserIdentityId(identityId.userId,identityId.providerId)
    }
  }

  def unapply(identityId:UserIdentityId):IdentityId = {
    new IdentityId(identityId.userId,identityId.providerId)
  }

  def findByIdentityId(identityId:IdentityId):Option[UserIdentityId] =
  {
    (select[UserIdentityId] where(_.userId :== identityId.userId,_.providerId :== identityId.providerId )).headOption
  }

}