package models


import core._
import models.PostgresConnection._
import net.fwbrasil.activate.entity.Entity
import securesocial.core.{IdentityId, Identity}
import securesocialpersistence.UserIdentity
import controllers.routes
import securesocial.core.IdentityId


class User extends Entity with Identity
{
  var currentIdentity:UserIdentity = _
  var currentSubDiscipline:SubDiscipline = _

  var isAdmin: Boolean = false  //this is very primitive but we must to study users roles properly before implementing a good solution for permissions

  var contributions:Int = 0
  var evaluations:Int = 0

  var affiliation:Option[String] = _
  var firstName:String = _
  var lastName:String = _
  var fullName:String = _
  var email:Option[String] = _
  var avatarUrl:Option[String] = _
  var phoneNumber:Option[String] = _
  var mailingAddress:Option[String] = _
  var mailingAddressCity:Option[String] = _
  var mailingAddressState:Option[String] = _
  var mailingAddressZipCode:Option[String] = _
  var website:Option[String] = _
  var about:Option[String] = _
  var socialProfileFacebook:Option[String] = _
  var socialProfileLinkedIn:Option[String] = _
  var socialProfileGoogle:Option[String] = _
  var socialProfileTwitter:Option[String] = _


  def identities: List[UserIdentity] = List.empty[UserIdentity]
  def identityId  = currentIdentity.identityId
  def setFirstNameWithCurrentIdentity() {
    if(firstName == null || firstName.isEmpty)
      firstName = currentIdentity.fullName.split(" ").head
    else
      firstName= currentIdentity.firstName
  }
  def setLastNameWithCurrentIdentity() {
    if(lastName == null || lastName.isEmpty)
      lastName = currentIdentity.fullName.split(" ").last
    else
      lastName = currentIdentity.lastName
  }
  def setEmailWithCurrentIdentity() {
    if(!this.email.isDefined)
      email = currentIdentity.email
  }
  def setFullNameWithCurrentIdentity() {
    if(fullName == null || fullName.isEmpty)
      fullName = currentIdentity.fullName
  }
  def setAvatarUrlWithCurrentIdentity() {
    if(!this.avatarUrl.isDefined)
      avatarUrl = currentIdentity.avatarUrl
  }
  def authMethod = currentIdentity.authMethod
  def oAuth1Info = currentIdentity.oAuth1Info
  def oAuth2Info = currentIdentity.oAuth2Info
  def passwordInfo = currentIdentity.passwordInfo
  def avatarUrlOrDefault = avatarUrl.getOrElse(User.defaultAvatarUrl)
  def setInfoForIdentity(identity:Identity){
    val userIdentity = UserIdentity.findByIdentity(identity).getOrElse(UserIdentity(this, identity))
    userIdentity.setDisplayInfoForIdentity(identity)
  }
  def setCurrentIdentity(userIdentity:UserIdentity){
    this.currentIdentity = userIdentity

    this.setFirstNameWithCurrentIdentity()
    this.setLastNameWithCurrentIdentity()
    this.setEmailWithCurrentIdentity()
    this.setFullNameWithCurrentIdentity()
    this.setAvatarUrlWithCurrentIdentity()
  }

  def ideasCreated:List[Idea] = Idea.ideaFromUser(this)

  def ideasFollowed:List[Idea] = IdeaUser.ideasFollowedByUser(this)

  def followers:List[User] = FollowerUser.findByFollowed(this).map(_.follower)

  def setSubDisciplineIfEmpty() {

    if (this.currentSubDiscipline == null)
    {
      this.currentSubDiscipline = SubDiscipline.defaultSubDiscipline
    }
  }

  def subDisciplinesOfInterest = SubDisciplineInterestedUser.findByUser(user = this).map(_.subDiscipline)

  def canEditUser(user:User) = isAdmin || user == this

}

object User
{

  def apply() = transactional {
    val user =  new User()

    user
  }

  def defaultAvatarUrl = routes.Assets.at("images/profile-placeholder.png")

  def findById(id:String): Option[User] = byId[User](id)

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
