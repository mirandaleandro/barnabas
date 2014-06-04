package models

import securesocial.core._
import models.PostgresConnection._
import securesocial.core.OAuth1Info
import securesocial.core.IdentityId

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

  def oAuth1Info:Option[OAuth1Info] = UserOAuth1Info.unapply(this.userOAuth1Info)

  def oAuth2Info:Option[OAuth2Info] = UserOAuth2Info.unapply(this.userOAuth2Info)

  def passwordInfo:Option[PasswordInfo] = UserPasswordInfo.unapply(this.userPasswordInfo)

  def equals(that:Any):Boolean =
  {
    if (that.isInstanceOf[Identity])
    {
      this.identityId == that.asInstanceOf[Identity].identityId
    }else
    {
      false
    }
  }

//    var user:User = _
//  var identityId : securesocial.core.IdentityId
//  var firstName : scala.Predef.String
//  var lastName : scala.Predef.String
//  var fullName : scala.Predef.String
//  var email : scala.Option[scala.Predef.String]
//  var avatarUrl : scala.Option[scala.Predef.String]
//  var authMethod : securesocial.core.AuthenticationMethod
//  var oAuth1Info : scala.Option[securesocial.core.OAuth1Info]
//  var oAuth2Info : scala.Option[securesocial.core.OAuth2Info]
//  var passwordInfo : scala.Option[securesocial.core.PasswordInfo]

}

object UserIdentity
{

  def apply(user:User, identity:Identity): UserIdentity = transactional{

    val identityId = UserIdentityId(identity.identityId)
    val authMethod = UserAuthenticationMethod(identity.authMethod)
    val oAuth1Info = identity.oAuth1Info.map(UserOAuth1Info(_))
    val oAuth2Info = identity.oAuth2Info.map(UserOAuth2Info(_))
    val passwordInfo = identity.passwordInfo.map(UserPasswordInfo(_))

    new UserIdentity(
      user = user,
      identityId = identityId,
      firstName = identity.firstName,
      lastName = identity.lastName,
      fullName = identity.fullName,
      email = identity.email,
      avatarUrl = identity.avatarUrl,
      authMethod = authMethod,
      oAuth1Info = oAuth1Info,
      oAuth2Info = oAuth2Info,
      passwordInfo = passwordInfo
    )
  }

  def findByIdentity(identity:Identity):Option[UserIdentity] = transactional
  {
    (select[UserIdentity] where(_.identityId :== identity.identityId)).headOption

  }

  def findByIdentityId(identityId:String):Option[UserIdentity] =
  {
    (select[UserIdentity] where(_.identityId :== identityId)).headOption
  }


}


class UserIdentityId(val userId : scala.Predef.String, val providerId : scala.Predef.String) extends Entity
object UserIdentityId
{
  def apply(identityId:IdentityId):UserIdentityId =
  {
    transactional{
      new UserIdentityId(identityId.userId,identityId.providerId)
    }
  }

  def unapply(identityId:UserIdentityId):IdentityId = {
    new IdentityId(identityId.userId,identityId.providerId)
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
}



class UserOAuth2Info(val accessToken : scala.Predef.String, val tokenType : scala.Option[scala.Predef.String]) extends Entity
object UserOAuth2Info
{
  def apply(auth:OAuth2Info):UserOAuth2Info =
  {
    transactional{
      new UserOAuth2Info(auth.accessToken,auth.tokenType)
    }
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
}

