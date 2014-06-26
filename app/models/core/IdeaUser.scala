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

  def likeIdea()
  {
    if (!like.isDefined)
    {
      this.idea.voted += 1
      this.user.evaluations += 1
    }

    this.idea.votedUp += 1

    this.like = Some(true)

  }

  def unlikeIdea()
  {
    if (!like.isDefined)
    {
      this.idea.voted += 1
      this.user.evaluations += 1
    }
    else
      this.idea.votedUp -= 1

    this.like = Some(false)
  }

  def setLikeWithBoolean(like:Boolean) {
    if (like)
      this.likeIdea()
    else
      this.unlikeIdea()
  }

  def flagInappropriateIdea(){ this.flaggedAsInappropriate = true}
  def unflagInappropriateIdea(){ this.flaggedAsInappropriate = false}
  def toggleFlagInappropriate(){this.flaggedAsInappropriate = !this.flaggedAsInappropriate}

  def followIdea(){
    follow = true
    idea.followersCount += 1
  }
  def unfollowIdea(){
    follow = false
    idea.followersCount -= 1
  }

  def toggleFollow(){
   if(follow)
     unfollowIdea()
   else
     followIdea()
  }

  def interestedInCollaborateWithIdea(){
    this.interestedInCollaborate = true
    this.user.contributions += 1
  }
  def uninterestedInCollaborateWithIdea(){
    this.interestedInCollaborate = false
    this.user.contributions -= 1
  }
  def toggleCollaborate(){ this.interestedInCollaborate = !this.interestedInCollaborate}

  def isLike:Boolean = this.like.isDefined && this.like.get
  def isDislike:Boolean = this.like.isDefined && !this.like.get
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

  def followersOfIdea(idea: Idea): List[User] = (select[IdeaUser] where(_.idea :== idea, _.follow :== true)).map(_.user)

  def interestedUsersInIdea(idea: Idea): List[User] = (select[IdeaUser] where(_.idea :== idea, _.interestedInCollaborate :== true)).map(_.user)

  def discussionsByIdea(idea:Idea): List[IdeaDiscussion] = findByIdea(idea).flatMap(_.discussion)

  def findByDiscussion(discussion: IdeaDiscussion) =  select[IdeaUser] where(_.discussion :== discussion )

  def findByUser(user:User): List[IdeaUser] = select[IdeaUser] where(_.user :== user )

  def findByIdea(idea:Idea): List[IdeaUser] = select[IdeaUser] where(_.idea :== idea )

  def findByIdeaAndUser(idea:Idea, user:User): Option[IdeaUser] = {
    (select[IdeaUser] where(_.idea :== idea, _.user :== user)).headOption
  }
}
