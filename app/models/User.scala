package models

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/3/14
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
import play.api.Application
import securesocial.core.{Identity, IdentityId, UserServicePlugin}
import securesocial.core.providers.Token
import models.PostgresConnection._
import net.fwbrasil.activate.entity.Entity


class User extends Entity with Identity
{
  var currentIdentity:UserIdentity = _
  def identities: List[UserIdentity] = List.empty[UserIdentity]

  def identityId  = currentIdentity.identityId
  def firstName = currentIdentity.firstName
  def lastName = currentIdentity.lastName
  def fullName = currentIdentity.fullName
  def email = currentIdentity.email
  def avatarUrl = currentIdentity.avatarUrl
  def authMethod = currentIdentity.authMethod
  def oAuth1Info = currentIdentity.oAuth1Info
  def oAuth2Info = currentIdentity.oAuth2Info
  def passwordInfo = currentIdentity.passwordInfo


}

object User
{
  def apply() = transactional { new User() }

}
