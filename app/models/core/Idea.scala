package models.core

import net.fwbrasil.activate.entity.Entity
import models.User

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 1:54 PM
 */
class Idea(var createdBy:User, var title:String, var description:String) extends Entity
{

  def authors:List[User] = ???
  def followers:List[User] = ???
  def interestedUsers:List[User] = ???

}
