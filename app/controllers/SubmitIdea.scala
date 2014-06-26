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
object SubmitIdea extends Controller with securesocial.core.SecureSocial
{
  val IMAGE_STORAGE_FOLDER = "public/images/richtext/"
  val IMAGE_ASSETS_FOLDER = "images/richtext/"

  val MAX_TOPICS_DISPLAYED = 16

  def richTextImageUpload = Action(parse.multipartFormData) { request =>
    request.body.file("file").map { picture =>
      val filename = CustomRandom.nextString(28)

      val path = String.format("%s%s",IMAGE_STORAGE_FOLDER, filename)

      val file =new java.io.File(path)

      picture.ref.moveTo(file)

      Ok(routes.Assets.at(IMAGE_ASSETS_FOLDER + filename).url)

    }.getOrElse {
      BadRequest("UPLOAD FAILED")
    }
  }

  case class IdeaSubmissionForm(var ideaId:String, var title:String, description:String, topics:List[String])
  {
    def idea:Option[Idea] = Idea.findById(ideaId)
  }

  val ideaSubmitionForm = Form(
    mapping(
      "ideaId" -> text,
      "title" -> text.verifying("title must be shorter than 140 characters", text => text.length <= 140),
      "description" -> text.verifying("Description cannot be empty", description => description.length > 0),
      "topics" -> list(text)
      )
      (IdeaSubmissionForm.apply)(IdeaSubmissionForm.unapply)
  )

  def submitIdea = SecuredAction{
    implicit request =>
      ideaSubmitionForm.bindFromRequest.fold(
        formWithErrors => BadRequest(formWithErrors.errorsAsJson),
        (form: IdeaSubmissionForm) => {
          transactional{

            implicit val user = request.user

            val idea =  form.idea.getOrElse{Idea(createdBy = user)}

            if(user.canEditIdea(idea))
            {
              idea.title = form.title
              idea.ideaPhase = IdeaPhase.Inception
              idea.description = form.description

              idea.addSubDiscipline(user.currentSubDiscipline)

              for(topicTitle <- form.topics)
              {
                val cleanTitle = topicTitle.trim()

                if( !cleanTitle.isEmpty )
                {
                  val topic = Topic.findBySubDisciplineAndTitle(user.currentSubDiscipline,cleanTitle).getOrElse{
                    Topic( createdBy = user, title = cleanTitle, subDiscipline = user.currentSubDiscipline)
                  }

                  idea.addTopic(topic = topic)
                }

              }

              Redirect(routes.Application.index())
            }else
            {
              BadRequest("Permission Denied")
            }
          }
        }
      )
  }

  def editIdea(ideaId:String) = SecuredAction{ implicit request =>
    transactional
    {
      implicit val user = request.user

      Idea.findById(ideaId).flatMap {
        idea =>
          if(user.canEditIdea(idea))
            Some(Ok(views.html.pages.submitIdea(Some(idea))))
          else
            None

      }.getOrElse{
          NotFound(views.html.errors.notFound(request.request))
      }
    }
  }

  def topics(term:String) = SecuredAction{
    implicit request =>
      implicit val user = request.user
      transactional{
        val stringTopics = Topic.findBySubDiscipline(user.currentSubDiscipline).map(_.title).filter(_.contains(term))

        Ok(Json.toJson(stringTopics))
      }
  }

  case class DisplayTopic(var checked:Boolean, var topic:Topic)

  def topicsToBeDisplayed(user:User, idea:Option[Idea]):List[DisplayTopic] = {
    var displayTopics: List[DisplayTopic] =  List[DisplayTopic]()

    idea.map{ idea =>
      displayTopics = displayTopics ++ idea.topics.map( topic =>  DisplayTopic(true,topic ))
    }

    val tempTopics = displayTopics.map(_.topic)
    displayTopics = displayTopics ++ user.currentSubDiscipline.topics.filter
    {
      disciplineTopic =>
        !tempTopics.contains(disciplineTopic)
    }.map( topic =>  DisplayTopic(false,topic ))

    displayTopics.take(MAX_TOPICS_DISPLAYED)
  }

}

object CustomRandom
{
  val alphanumericCharSet = (('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')).toList

  def nextString(length: Int): String = {
    randomStringFromCharList(length, alphanumericCharSet)
  }

  def randomStringFromCharList(length: Int, chars: Seq[Char]): String = {
    val sb = new StringBuilder
    for (i <- 1 to length) {
      val randomNum = Random.nextInt(chars.length)
      sb.append(chars(randomNum))
    }
    sb.toString()
  }
}