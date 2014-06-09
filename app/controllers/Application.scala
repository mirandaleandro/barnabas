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
    Play.current.configuration.getString("your.key")

    Ok(views.html.index("Your new application is ready."))

  }

  def dashboard = index

  def submitIdeas = Action { implicit request =>
    Ok(views.html.pages.submitIdea())
  }

  def getInspired = Action { implicit request =>
    Ok(views.html.pages.getInspired())
  }

  def evaluateIdeas() = Action { implicit request =>
    Ok(views.html.pages.evaluateIdeas())
  }

}