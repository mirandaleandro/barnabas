package controllers

import play.api._
import play.api.mvc._
import securesocial.core.Authorization
import securesocial.core.Identity
import securesocial.core.{IdentityId, UserService, Identity, Authorization}
import play.api.{Logger, Play}
import service.UserService
import models.PostgresConnection._
import models.User
import reflect.io.File
import util.Random
import java.io
import models.core.{IdeaUser, Idea}

object Application extends Controller with securesocial.core.SecureSocial{

  val defaultDateFormat = new java.text.SimpleDateFormat("dd/MMM/yy")

  def index = SecuredAction {
    implicit request =>
      transactional{
        implicit val user = request.user
        Logger.warn("logging from application")
        Play.current.configuration.getString("your.key")

        Ok(views.html.index())
      }

  }

  def dashboard = index

  def submitIdeas = SecuredAction { implicit request =>
    transactional{
      implicit val user = request.user
      Ok(views.html.pages.submitIdea())
    }
  }

  def getInspired = SecuredAction { implicit request =>
    transactional{
      implicit val user = request.user
      Ok(views.html.pages.getInspired())
    }
  }

  def idea(ideaId:String) = {
    SecuredAction { implicit request =>
      transactional{
        implicit val user = request.user

        Idea.findById(ideaId).map{ idea =>

          val evaluation = IdeaUser.findByIdeaAndUser(idea = idea, user = user).getOrElse(IdeaUser(user = user, idea = idea))

          Ok(views.html.pages.idea(evaluation = evaluation))

        }.getOrElse{
          NotFound
        }

      }
    }
  }
}