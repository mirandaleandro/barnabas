package models.securesocialpersistence

import _root_.securesocial.core.{IdentityId, Identity}
import securesocial.core._
import models.PostgresConnection._
import securesocial.core.OAuth1Info
import securesocial.core.IdentityId
import models.User

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/3/14
 * Time: 5:54 PM
 * To change this template use File | Settings | File Templates.
 */
class UserIdentity (
                     var user:User,
                     var userIdentityId: UserIdentityId,
                     var firstName:String,
                     var lastName:String,
                     var fullName:String,
                     var email:Option[String],
                     var avatarUrl:Option[String],
                     var userAuthMethod:UserAuthenticationMethod,
                     var userOAuth1Info:Option[UserOAuth1Info],
                     var userOAuth2Info:Option[UserOAuth2Info],
                     var userPasswordInfo:Option[UserPasswordInfo]) extends Entity with Identity
{

  def identityId:IdentityId = UserIdentityId.unapply(this.userIdentityId)

  def authMethod:AuthenticationMethod = UserAuthenticationMethod.unapply(this.userAuthMethod)

  def oAuth1Info:Option[OAuth1Info] = this.userOAuth1Info.map(UserOAuth1Info.unapply(_))

  def oAuth2Info:Option[OAuth2Info] = this.userOAuth2Info.map(UserOAuth2Info.unapply(_))

  def passwordInfo:Option[PasswordInfo] = this.userPasswordInfo.map(UserPasswordInfo.unapply(_))

  override def equals(that:Any):Boolean =
  {
    if (that.isInstanceOf[Identity])
    {
      this.identityId == that.asInstanceOf[Identity].identityId
    }else
    {
      false
    }
  }

  def setDisplayInfoForIdentity(identity:Identity)
  {
    this.firstName = identity.firstName
    this.lastName = identity.lastName
    this.fullName = identity.fullName
    this.email = identity.email
    this.avatarUrl = identity.avatarUrl
  }
}

object UserIdentity
{


  def apply(user:User, identity:Identity): UserIdentity = transactional{

    val userIdentityId: UserIdentityId = UserIdentityId(identity.identityId)
    val userAuthMethod = UserAuthenticationMethod(identity.authMethod)
    val userOAuth1Info = identity.oAuth1Info.map(UserOAuth1Info(_))
    val userOAuth2Info = identity.oAuth2Info.map(UserOAuth2Info(_))
    val userPasswordInfo = identity.passwordInfo.map(UserPasswordInfo(_))

    val userIdentity =
      new UserIdentity(
        user = user,
        userIdentityId = userIdentityId,
        firstName = identity.firstName,
        lastName = identity.lastName,
        fullName = identity.fullName,
        email = identity.email,
        avatarUrl = identity.avatarUrl,
        userAuthMethod = userAuthMethod,
        userOAuth1Info = userOAuth1Info,
        userOAuth2Info = userOAuth2Info,
        userPasswordInfo = userPasswordInfo
      )

    user.currentIdentity = userIdentity

    userIdentity
  }

  def findByIdentity(identity:Identity):Option[UserIdentity] = transactional
  {
    findByIdentityId(identityId = identity.identityId)
  }

  def findByIdentityId(identityId:IdentityId):Option[UserIdentity] =
    UserIdentityId.findByIdentityId(identityId).flatMap{
      u =>
      (select[UserIdentity] where( _.userIdentityId :== u )).headOption

  }

  def findByEmailAndProvider(email: String, provider: String):Option[UserIdentity] =
  {
    //TODO

    None
  }



}




class UserAuthenticationMethod(val method : scala.Predef.String) extends Entity
object UserAuthenticationMethod
{
  def apply(authMethod:AuthenticationMethod):UserAuthenticationMethod =
  {
    transactional{
      new UserAuthenticationMethod(authMethod.method)
    }
  }

  def unapply(userAuthenticationMethod:UserAuthenticationMethod):AuthenticationMethod = {
    new AuthenticationMethod(userAuthenticationMethod.method)
  }

}



class UserOAuth1Info(val token : scala.Predef.String, val secret : scala.Predef.String) extends Entity
object UserOAuth1Info
{
  def apply(auth:OAuth1Info):UserOAuth1Info =
  {
    transactional{
      new UserOAuth1Info(auth.token,auth.secret)
    }
  }

  def unapply(userOAuth1Info:UserOAuth1Info):OAuth1Info = {
    new OAuth1Info(userOAuth1Info.token,userOAuth1Info.secret)
  }
}



class UserOAuth2Info(val accessToken:String, val tokenType:Option[String]) extends Entity
object UserOAuth2Info
{
  def apply(auth:OAuth2Info):UserOAuth2Info =
  {
    transactional{
      new UserOAuth2Info(auth.accessToken,auth.tokenType)
    }
  }

  def unapply(userOAuth2Info:UserOAuth2Info):OAuth2Info = {
    new OAuth2Info(userOAuth2Info.accessToken, userOAuth2Info.tokenType)
  }
}



class UserPasswordInfo(val hasher : scala.Predef.String, val password : scala.Predef.String, val salt : scala.Option[scala.Predef.String]) extends Entity
object UserPasswordInfo
{
  def apply(passInfo:PasswordInfo):UserPasswordInfo =
  {
    transactional{
      new UserPasswordInfo(passInfo.hasher, passInfo.password, passInfo.salt)
    }
  }

  def unapply(userPasswordInfo:UserPasswordInfo):PasswordInfo = {
    new PasswordInfo(userPasswordInfo.hasher, userPasswordInfo.password, userPasswordInfo.salt)
  }
}

