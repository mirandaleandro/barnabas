package models.core

import net.fwbrasil.activate.entity.Entity
import models.User
import models.PostgresConnection._
/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/15/14
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
class IdeaDiscussion(var createdBy:User,
                     var description:String,
                     var parentDiscussion:Option[IdeaDiscussion] = None,
                     var isAnonymous:Boolean = false,
                     var likes:Long = 0) extends Entity{

}

object IdeaDiscussion
{
  def findById(id: String) = byId[IdeaDiscussion](id)

  def apply(createdBy:User):IdeaDiscussion =
  {
    new IdeaDiscussion(createdBy = createdBy, description = "")
  }

  def apply(createdBy:User, description:String,  parentDiscussion:Option[IdeaDiscussion] = None, isAnonymous:Boolean = false, likes:Long = 0):IdeaDiscussion =
  {
      new IdeaDiscussion(createdBy = createdBy, description = description, parentDiscussion = parentDiscussion, isAnonymous = isAnonymous, likes = likes)
  }
}
