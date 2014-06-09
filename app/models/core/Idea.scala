package models.core

import net.fwbrasil.activate.entity.Entity
import models.User

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 1:54 PM
 */
class Idea(var createdBy:User, var title:String, var description:String, var ideaPhase:IdeaPhase) extends Entity
{

  def authors:List[User] = ???
  def followers:List[User] = ???
  def interestedUsers:List[User] = ???
  def resources:List[Resource] = ???

}

object Idea
{
  def apply(createdBy:User, title:String, description:String, ideaPhase:IdeaPhase):Idea =
  {
    new Idea(createdBy = createdBy, title = title, description = description, ideaPhase = ideaPhase)
  }
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
