package controllers


import play.api.mvc.{Action, Controller}
import util.Random
import play.api.data.Form
import play.api.data.Forms._
import models.PostgresConnection._
import models.core.{SubDiscipline, Topic, IdeaPhase, Idea}
import models.User
import play.api.libs.json.Json

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/11/14
 * Time: 12:11 AM
 * To change this template use File | Settings | File Templates.
 */
object EvaluateIdeas extends Controller with securesocial.core.SecureSocial
{

  def evaluateIdeas() =
    SecuredAction { implicit request =>
    transactional{
      implicit val user = request.user

      //val ideaForEvaluation = Idea.ideaForEvaluate(user = user)

      //Ok(views.html.pages.evaluateIdeas(idea = ideaForEvaluation))
      Ok(views.html.pages.evaluateIdeas())

    }
  }

  def evaluateIdeasWithId(ideaId:String) = SecuredAction { implicit request =>
    transactional{
      implicit val user = request.user
      Ok(views.html.pages.evaluateIdeas())
    }
  }


}