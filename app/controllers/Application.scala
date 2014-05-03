package controllers

import play.api._
import play.api.mvc._
import securesocial.core.Authorization
import securesocial.core.Identity
import securesocial.core.{IdentityId, UserService, Identity, Authorization}
import play.api.{Logger, Play}
import service.UserService

object Application extends Controller with securesocial.core.SecureSocial{


  def index = SecuredAction { implicit request =>
    Logger.warn("logging from application")
    Ok(views.html.index("Your new application is ready."))
  }

}