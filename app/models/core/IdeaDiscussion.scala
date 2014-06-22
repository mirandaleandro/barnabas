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


  def children = {
    IdeaDiscussion.childrenDiscussionsOfDiscussion(this).sortBy(_.creationDate)
  }

  def closest = parentDiscussion.getOrElse(this)

  def evaluation = IdeaUser.findByDiscussion(discussion = closest).headOption

  def shortenedDescription(numberOfCharacters:Int):String = {
    val escapedDescription = description.replaceAll("""<(?!\/?a(?=>|\s.*>))\/?.*?>""", "")
    if (escapedDescription.length > numberOfCharacters)
      escapedDescription.substring(0,Math.min(escapedDescription.length, numberOfCharacters )) + "..."
    else
      escapedDescription
  }

  def creatorName = {
    if (isAnonymous)
      "Anonymous"
    else
      createdBy.firstName + " " + createdBy.lastName
  }

  def creatorAvatar = {
    if (isAnonymous)
      User.defaultAvatarUrl
    else
      createdBy.avatarUrlOrDefault
  }



}

object IdeaDiscussion
{

  def apply(createdBy:User):IdeaDiscussion =
  {
    new IdeaDiscussion(createdBy = createdBy, description = "")
  }

  def apply(createdBy:User, description:String,  parentDiscussion:Option[IdeaDiscussion] = None, isAnonymous:Boolean = false, likes:Long = 0):IdeaDiscussion =
  {
      new IdeaDiscussion(createdBy = createdBy, description = description, parentDiscussion = parentDiscussion, isAnonymous = isAnonymous, likes = likes)
  }

  def findById(id: String) = byId[IdeaDiscussion](id)

  def childrenDiscussionsOfDiscussion(discussion:IdeaDiscussion) = query {
      (dbDiscussion: IdeaDiscussion) =>
        where(dbDiscussion.parentDiscussion :== discussion) select (dbDiscussion)
    }

}
