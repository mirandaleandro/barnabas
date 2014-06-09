package models.core

import models.User
import net.fwbrasil.activate.entity.Entity

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
class IdeaInterestedUser(var interestedUser:User, var idea:Idea) extends Entity
{

}

object IdeaInterestedUser
{
  def apply(interestedUser:User, idea:Idea):IdeaInterestedUser =
  {
    val ideaInterestedUser = new IdeaInterestedUser(interestedUser = interestedUser, idea = idea)

    idea.collaboratorsCount += 1

    ideaInterestedUser
  }
}
