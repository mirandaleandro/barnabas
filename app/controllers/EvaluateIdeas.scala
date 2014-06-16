package controllers


import play.api.mvc.{Action, Controller}
import util.Random
import play.api.data.Form
import play.api.data.Forms._
import models.PostgresConnection._
import models.core._
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

      val idea = Idea.ideaForEvaluation(user = user)

      idea.map { idea =>
        IdeaUser(idea = idea, user = user)
        Redirect(routes.EvaluateIdeas.evaluateIdeasWithId(idea.id))
      }.getOrElse{
          NotFound
      }
    }
  }

  def evaluateIdeasWithId(ideaId:String) = SecuredAction { implicit request =>
    transactional{
      implicit val user = request.user

      Idea.findById(ideaId).map { idea =>

          val ideaUser = IdeaUser.findByIdeaAndUser(idea = idea, user = user).getOrElse(IdeaUser(idea = idea, user = user))

          Ok(views.html.pages.evaluateIdeas(evaluation = ideaUser))

      }.getOrElse{
        NotFound
      }
    }
  }

  case class ResourceForm(var evaluationId:String, var description:String, var resourceTypeId:String, var link:Option[String])

  val addResourceForm = Form(
    mapping(
      "evaluationId" -> nonEmptyText,
      "description" -> nonEmptyText,
      "resourceTypeId" -> nonEmptyText.verifying("ResourceType does not exist", id => transactional{ResourceType.findById(id).isDefined }),
      "link" -> optional(text)
    )
      (ResourceForm.apply)(ResourceForm.unapply)
  )

  def addResource() = SecuredAction{  implicit request =>
    transactional{
      implicit val user = request.user

      addResourceForm.bindFromRequest.fold(
        (formWithErrors: Form[ResourceForm]) => BadRequest(formWithErrors.errorsAsJson),
        form => {

          val evaluation: Option[IdeaUser] = IdeaUser.findById(form.evaluationId)

          evaluation.map{ evaluation =>

            val resourceType = ResourceType.findById(form.resourceTypeId).get

            val resource = Resource.findBy(title = form.description, url = form.link, resourceType = resourceType ) .getOrElse{
              Resource(createdBy = user, title = form.description, url = form.link, resourceType = resourceType)
            }

            evaluation.idea.addResource(resource = resource, suggestedBy = user)

            Ok(views.html.utils.resourcesTable(evaluation))

          }.getOrElse{
            BadRequest
          }
        })
    }
  }

}