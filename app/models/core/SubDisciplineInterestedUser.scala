package models.core

import models.User
import net.fwbrasil.activate.entity.Entity
import models.PostgresConnection._

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
class SubDisciplineInterestedUser(var user:User, var subDiscipline:SubDiscipline) extends Entity{

}

object SubDisciplineInterestedUser{

  def apply(user:User, subDiscipline:SubDiscipline) = new SubDisciplineInterestedUser(user = user,subDiscipline = subDiscipline)

  def findByUser(user:User) = select[SubDisciplineInterestedUser] where(_.user :== user)

}
