package controllers

import play.mvc.Controller
import models.PostgresConnection._
import models.core.IdeaUser

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/22/14
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
object TopMenu extends Controller with securesocial.core.SecureSocial{

  def updateMessagesCheckIn() = SecuredAction{ implicit request =>
    transactional{
      implicit val user = request.user
        user.updateMessagesCheckIn()
      Ok
    }
  }

}
