package service

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/3/14
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */

import play.api.Logger
import models.{SecureSocialToken, User}
import play.api.Application
import securesocial.core.{Identity, IdentityId, UserServicePlugin}
import securesocial.core.providers.Token
import models.PostgresConnection._

class SecureSocialService(application: Application) extends UserServicePlugin(application) {
  /**
   * Finds a user that maches the specified id
   *
   * @param id the user id
   * @return an optional user
   */
  def find(id: IdentityId):Option[Identity] = {
    transactional {
      val user = User.findByIdentityId(id)
      if (!user.isDefined)
      {
        Logger.logger.info("User not defined")
      }
      user
    }
  }

  /**
   * Finds a user by email and provider id.
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation.
   *
   * @param email - the user email
   * @param providerId - the provider id
   * @return
   */
  def findByEmailAndProvider(email: String, providerId: String):Option[Identity] =
  {
      User.findByEmailAndProvider(email, providerId)
  }

  /**
   * Saves the user.  This method gets called when a user logs in.
   * This is your chance to save the user information in your backing store.
   * @param user
   */
  def save(user: Identity):Identity =
  {
    val sysUser = User.findByIdentity(user).getOrElse(User())
    sysUser.setInfoForIdentity(user)
    sysUser
  }

  /**
   * Saves a token.  This is needed for users that
   * are creating an account in the system instead of using one in a 3rd party system.
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation
   *
   * @param token The token to save
   */
  def save(token: Token) = {
    SecureSocialToken(token)
  }


  /**
   * Finds a token
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation
   *
   * @param token the token id
   * @return
   */
  def findToken(token: String): Option[Token] = {
    SecureSocialToken.tokenByUuid(token)
  }

  /**
   * Deletes a token
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation
   *
   * @param uuid the token id
   */
  def deleteToken(uuid: String) {
    SecureSocialToken.deleteToken(uuid)
  }

  /**
   * Deletes all expired tokens
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation
   *
   */
  def deleteExpiredTokens() {
    SecureSocialToken.deleteExpiredTokens()
  }
}