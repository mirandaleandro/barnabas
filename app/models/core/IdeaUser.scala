package models.core

import net.fwbrasil.activate.entity.Entity
import models.User
import models.PostgresConnection._
import play.Logger

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/15/14
 * Time: 4:11 PM
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
    Logger.info("IdeaUser user:"+user.fullName + " idea: "+idea.title)

    new IdeaUser(user = user, idea = idea, like = like, discussion = discussion)
  }


  def findById(id:String) = byId[IdeaUser](id)

  def ideasFollowedByUser(user: User): List[Idea] = (select[IdeaUser] where(_.user :== user)).map(_.idea)

  def findByUser(user:User): List[IdeaUser] = select[IdeaUser] where(_.user :== user )

  def findByIdeaAndUser(idea:Idea, user:User): Option[IdeaUser] = {
    (select[IdeaUser] where(_.idea :== idea, _.user :== user)).headOption
  }
}
