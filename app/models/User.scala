package models


import core.{IdeaUser, SubDiscipline, Idea}
import models.PostgresConnection._
import net.fwbrasil.activate.entity.Entity
import securesocial.core.{IdentityId, Identity}
import securesocialpersistence.UserIdentity


class User extends Entity with Identity
{

  var currentIdentity:UserIdentity = _
  var currentSubDiscipline:SubDiscipline = _
  var affiliation:Option[String] = _
  var contributions:Int = 0
  var evaluations:Int = 0

  def identities: List[UserIdentity] = List.empty[UserIdentity]

  def identityId  = currentIdentity.identityId

  def firstName: String = {
    if(currentIdentity.firstName.isEmpty)
      currentIdentity.fullName.split(" ").head
    else
      currentIdentity.firstName
  }

  def lastName: String ={
    if(currentIdentity.lastName.isEmpty)
      currentIdentity.fullName.split(" ").last
    else
      currentIdentity.lastName
  }

  def fullName: String = currentIdentity.fullName
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

  def ideasFollowed:List[Idea] = IdeaUser.ideasFollowedByUser(this)

  def setSubDisciplineIfEmpty() = {

    if (this.currentSubDiscipline == null)
    {
      this.currentSubDiscipline = SubDiscipline.defaultSubDiscipline
    }
  }


}

object User
{

  def apply() = transactional {
    val user =  new User()


    user
  }

  def findAll: List[User] = all[User]

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
