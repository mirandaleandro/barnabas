package models


import core.{IdeaFollower, Idea}
import models.PostgresConnection._
import net.fwbrasil.activate.entity.Entity
import securesocial.core.{IdentityId, Identity}
import securesocialpersistence.UserIdentity


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

  def ideasCreated:List[Idea] = Idea.ideaFromUser(this)

  def ideasFollowed:List[Idea] = IdeaFollower.ideasFollowedByUser(this)

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

  def findByIdentity(identity: Identity):Option[User] = UserIdentity.findByIdentity(identity).map(_.user)

}
