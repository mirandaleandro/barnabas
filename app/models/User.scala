package models


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

  def setInfoForIdentity(identity:Identity)
  {
     val userIdentity = UserIdentity.findByIdentity(identity).getOrElse(UserIdentity(this, identity))
     userIdentity.setDisplayInfoForIdentity(identity)
  }

}

object User
{

  def apply() = transactional { new User() }


  def findByIdentityId(id: IdentityId): Option[User] =
  {
      UserIdentity.findByIdentityId(id).map(_.user)
  }

  def findByEmailAndProvider(email: String, provider: String): Option[User] =
  {
     UserIdentity.findByEmailAndProvider(email,provider).map(_.user)
  }

  def findByIdentity(identity: Identity):Option[User] = ???

}
