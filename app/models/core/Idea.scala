package models.core

import net.fwbrasil.activate.entity.Entity
import models.User
import models.PostgresConnection._

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 1:54 PM
 */
class Idea(var createdBy:User,
           var title:String,
           var description:String,
           var ideaPhase:IdeaPhase,
           var visited:Long = 0,
           var voted:Long = 0,
           var followersCount:Long = 0,
           var collaboratorsCount:Long = 0) extends Entity
{

  def authors:List[User] = ???  //we will probably wish to have coauthoring of ideas. By now we will just use "createdBy"
  def followers:List[User] = ???
  def interestedUsers:List[User] = ???
  def resources:List[Resource] = ???
  def phase = this.ideaPhase.title
  def feedbackRatio:Float = {
    if (this.voted == 0)
      0
    else
      this.visited/this.voted
  }


}

object Idea
{
  def apply(createdBy:User, title:String, description:String, ideaPhase:IdeaPhase):Idea =
  {
    new Idea(createdBy = createdBy, title = title, description = description, ideaPhase = ideaPhase)
  }

  def ideaFromUser(user:User):List[Idea] = select[Idea] where( _.createdBy :== user )

  def ideaAtPhase(ideaPhase:IdeaPhase):List[Idea] = select[Idea] where( _.ideaPhase :== ideaPhase )
}

class IdeaPhase(var createdBy:User, var title:String ) extends Entity
{

}

object IdeaPhase
{
  def apply(createdBy:User, title:String):IdeaPhase =
  {
    new IdeaPhase(createdBy = createdBy, title = title)
  }


}
