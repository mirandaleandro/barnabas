package models.core

import net.fwbrasil.activate.entity.Entity
import models.User

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 1:54 PM
 */
class Idea extends Entity
{
  var title:String = _
  var description:String = _
  var creator:User = _

  def authors:List[User] = ???
  def followers:List[User] = ???
  def interestedUsers:List[User] = ???

}
