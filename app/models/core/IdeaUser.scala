package models.core

import net.fwbrasil.activate.entity.Entity
import models.User
import models.PostgresConnection._


/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/15/14
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
class IdeaUser(var user:User, var idea:Idea, var like:Option[Boolean] = None, var discussion:Option[IdeaDiscussion] = None) extends Entity{

  var flaggedAsInappropriate:Boolean = false
  var follow:Boolean = false
  var interestedInCollaborate:Boolean = false
  var author:Boolean = false

  def likeIdea(){ this.like = Some(true) }

  def unlikeIdea(){ this.like = Some(false)}

  def followIdea(){ this.follow = true}
  def unfollowIdea(){ this.follow = false}

  def interestedInCollaborateWithIdea(){ this.interestedInCollaborate = true}
  def uninterestedInCollaborateWithIdea(){ this.interestedInCollaborate = false}

}

object IdeaUser
{

  def apply(user:User, idea:Idea, like:Option[Boolean] = None, discussion:Option[IdeaDiscussion] = None):IdeaUser =
  {
    new IdeaUser(user = user, idea = idea, like = like, discussion = discussion)
  }

  def ideasFollowedByUser(user: User): List[Idea] = (select[IdeaUser] where(_.user :== user)).map(_.idea)
}
