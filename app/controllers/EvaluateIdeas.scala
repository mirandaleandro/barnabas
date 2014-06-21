package controllers


import play.api.mvc.{Action, Controller}
import util.Random
import play.api.data.Form
import play.api.data.Forms._
import models.PostgresConnection._
import models.core._
import models.User
import play.api.libs.json.Json
import com.esotericsoftware.kryo.serializers.FieldSerializer.Optional

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

          idea.visited += 1

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


  case class LikeResourceForm(var ideaResourceId:String)
  val likeResourceForm = Form(
    mapping(
      "ideaResourceId" -> nonEmptyText
    )
      (LikeResourceForm.apply)(LikeResourceForm.unapply)
  )
  def likeResource() = SecuredAction{ implicit request =>
    transactional{
      implicit val user = request.user

      likeResourceForm.bindFromRequest.fold(
        formWithErrors => BadRequest(formWithErrors.errorsAsJson),
        form => {

          IdeaResource.findById(form.ideaResourceId).map { ideaResource =>

            val evaluation = IdeaResourceEvaluation.findByUserAndResource(user = user, ideaResource = ideaResource)

            if(evaluation.isDefined)
              evaluation.get.delete
            else
              IdeaResourceEvaluation(user,ideaResource)

            Ok(views.html.utils.resourceLike(ideaResource = ideaResource))

          }.getOrElse
          {
            BadRequest
          }

        })
    }
  }

  def voteIdea(ideaUserId:String,vote:Boolean) = SecuredAction{ implicit request =>
    transactional{
      implicit val user = request.user

      IdeaUser.findById(ideaUserId).map { ideaUser =>
        ideaUser.setLikeWithBoolean(vote)
        Ok(views.html.utils.votePool(ideaUser))
      }.getOrElse {
        BadRequest
      }
    }
  }


  case class IdeaDiscussionForm(discussionId:Option[String], ideaUserId:Option[String], description:String, anonymous:Option[Boolean],parentDiscussionId:Option[String])
  val ideaDiscussionForm = Form(
    mapping(
      "discussionId" -> optional(text),
      "ideaUserId" -> optional(text),
      "description" -> nonEmptyText,
      "anonymous" -> optional(boolean),
      "parentDiscussionId" -> optional(text)
    )
      (IdeaDiscussionForm.apply)(IdeaDiscussionForm.unapply)
  )
  def updateDiscussion() = SecuredAction{ implicit request =>
    transactional{
      implicit val user = request.user
        ideaDiscussionForm.bindFromRequest.fold(
        formWithErrors => BadRequest(formWithErrors.errorsAsJson),
        form => {

          val discussion = form.discussionId.flatMap(IdeaDiscussion.findById(_)).getOrElse(IdeaDiscussion(createdBy = user))
          val parentDiscussion: Option[IdeaDiscussion] = form.parentDiscussionId.flatMap( IdeaDiscussion.findById(_) )
          val ideaUser = form.ideaUserId.flatMap(IdeaUser.findById(_))
          val anonymous = form.anonymous.getOrElse(false)

          discussion.description = form.description
          discussion.isAnonymous = anonymous
          discussion.parentDiscussion = parentDiscussion

          ideaUser.map(_.discussion = Some(discussion))

          //The response below is used only by discussion replies and are never used to update anything when creating discussion from scretch
          Ok(views.html.utils.discussionReply(discussion))

        })
    }
  }

  def follow(ideaUserId:String) = SecuredAction{ implicit request =>
    transactional{
      implicit val user = request.user

      IdeaUser.findById(ideaUserId).map { ideaUser =>

        ideaUser.toggleFollow()

        Ok(views.html.utils.follow(ideaUser))

      }.getOrElse{

        BadRequest

      }
    }
  }

  def collaborate(ideaUserId:String) = SecuredAction{ implicit request =>
    transactional{
      implicit val user = request.user

      IdeaUser.findById(ideaUserId).map { ideaUser =>

        ideaUser.toggleCollaborate()

        Ok(views.html.utils.collaborate(ideaUser))

      }.getOrElse{

        BadRequest

      }
    }
  }
}