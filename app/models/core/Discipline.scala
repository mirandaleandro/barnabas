package models.core

import net.fwbrasil.activate.entity.Entity
import models.User

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */
class Discipline(var title:String, var createdBy:User) extends Entity{

}


object Discipline
{
  def apply(title:String, createdBy:User):Discipline =
  {
    new Discipline(title = title, createdBy = createdBy)

  }
}
