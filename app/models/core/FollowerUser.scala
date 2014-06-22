package models.core

import models.User
import models.PostgresConnection._

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/21/14
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */
class FollowerUser(var followed:User, var follower:User) extends Entity{

}

object  FollowerUser{

  def apply(followed:User, follower:User) = {
    new FollowerUser(followed = followed, follower = follower)
  }

  def findByFollowed(followed:User) = select[FollowerUser] where(_.followed :== followed)

  def findByFollowedAndFollower(followed: User, follower: User) = {
    (select[FollowerUser] where(_.followed :== followed, _.follower :== follower) ).headOption
  }
}
