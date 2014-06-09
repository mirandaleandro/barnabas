package models.core

import models.User
import net.fwbrasil.activate.entity.Entity

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 2:26 PM
 */


/*
  likeCounter is not acid, but its a good way to avoid unnecessary expensive queries
 */

class IdeaResource(var suggestedBy:User, var idea:Idea, var resource:Resource, var likeCounter:Long = 0) extends Entity{

}

object IdeaResource
{

  def apply(suggestedBy:User, idea:Idea, resource:Resource, likeCounter:Long = 0):IdeaResource =
  {
    new IdeaResource(suggestedBy = suggestedBy, idea = idea, resource = resource, likeCounter = likeCounter)
  }

}
