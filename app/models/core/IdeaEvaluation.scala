package models.core

import models.User
import net.fwbrasil.activate.entity.Entity

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
class IdeaEvaluation(var evaluator:Option[User], var idea:Idea) extends Entity
{

  /* If user is None than evaluation was made anonymously */
  def isAnonymous = !this.evaluator.isDefined

}
