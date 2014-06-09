package models.core

import models.User
import net.fwbrasil.activate.entity.Entity
import models.PostgresConnection._

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
class IdeaFollower(var follower:User, var idea:Idea) extends Entity
{

}

object IdeaFollower
{
  def apply(follower:User, idea:Idea):IdeaFollower =
  {
    new IdeaFollower(follower = follower, idea = idea)
  }

  def ideasFollowedByUser(user:User):List[Idea] = ( select[IdeaFollower] where(_.follower :== user) ).map(_.idea)

  def usersFollowingIdea(idea:Idea):List[User] = ( select[IdeaFollower] where(_.idea :== idea) ).map(_.follower)
}
