package controllers

import play.api.mvc.{Action, Controller}
import play.api.data.Form
import play.api.data.Forms._
import models.PostgresConnection._
import models.User
import models.core.{SubDisciplineInterestedUser, SubDiscipline, IdeaUser}
import play.api.libs.json.Json


/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/21/14
 * Time: 7:54 PM
 * To change this template use File | Settings | File Templates.
 */
object UserProfile extends Controller with securesocial.core.SecureSocial {

  val IMAGE_STORAGE_FOLDER = "public/images/custom_profile_pics/"
  val IMAGE_ASSETS_FOLDER = "images/custom_profile_pics/"



  case class BasicUserInformationForm(var userId:String,
                                      var affiliation:Option[String],
                                      var firstName:String,
                                      var lastName:String,
                                      var fullName:String,
                                      var email:String,
                                      var phoneNumber:Option[String],
                                      var mailingAddress:Option[String],
                                      var mailingAddressCity:Option[String],
                                      var mailingAddressState:Option[String],
                                      var mailingAddressZipCode:Option[String],
                                      var website:Option[String],
                                      var about:Option[String],
                                      var socialProfileFacebook:Option[String],
                                      var socialProfileLinkedIn:Option[String],
                                      var socialProfileGoogle:Option[String],
                                      var socialProfileTwitter:Option[String]
                                      )




  val basicUserInformationForm = Form(
    mapping(

      "userId" -> nonEmptyText,
      "affiliation" -> optional(text),
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "fullName" -> nonEmptyText,
      "email" -> nonEmptyText,
      "phoneNumber" -> optional(text),
      "mailingAddress" -> optional(text),
      "mailingAddressCity" -> optional(text),
      "mailingAddressState" -> optional(text),
      "mailingAddressZipCode" -> optional(text),
      "website" -> optional(text),
      "about" -> optional(text),
      "socialProfileFacebook" -> optional(text),
      "socialProfileLinkedIn" -> optional(text),
      "socialProfileGoogle" -> optional(text),
      "socialProfileTwitter" -> optional(text)

    )(BasicUserInformationForm.apply)(BasicUserInformationForm.unapply)
  )

  def updateProfile() = SecuredAction{
    implicit request =>
      basicUserInformationForm.bindFromRequest.fold(
        formWithErrors => BadRequest(formWithErrors.errorsAsJson),
        (form: BasicUserInformationForm) => {
          transactional{
            implicit val user = request.user

              User.findById(form.userId).map { pageUser =>

              if(user.canEditUser(pageUser))
              {
                pageUser.affiliation = form.affiliation
                pageUser.firstName = form.firstName
                pageUser.lastName = form.lastName
                pageUser.fullName = form.fullName
                pageUser.email = Some(form.email)
                pageUser.phoneNumber = form.phoneNumber
                pageUser.mailingAddress = form.mailingAddress
                pageUser.mailingAddressCity = form.mailingAddressCity
                pageUser.mailingAddressState = form.mailingAddressState
                pageUser.mailingAddressZipCode = form.mailingAddressZipCode
                pageUser.website = form.website
                pageUser.about = form.about
                pageUser.socialProfileFacebook = form.socialProfileFacebook
                pageUser.socialProfileGoogle = form.socialProfileGoogle
                pageUser.socialProfileLinkedIn = form.socialProfileLinkedIn
                pageUser.socialProfileTwitter = form.socialProfileTwitter

                Ok

              }else{
               BadRequest("Permission Denied")
              }
            }.getOrElse{
              BadRequest("Could not find user info")
            }

          }
        }
      )
  }


  def profilePicUpload = SecuredAction(parse.multipartFormData) { request =>
      request.request.body.file("file").map { picture =>
        transactional{
          implicit val user = request.user

          val filename = CustomRandom.nextString(28)

          val path = String.format("%s%s",IMAGE_STORAGE_FOLDER, filename)

          val file =new java.io.File(path)

          picture.ref.moveTo(file)

          user.avatarUrl = Some(routes.Assets.at(IMAGE_ASSETS_FOLDER + filename).url)

          Ok(user.avatarUrl.get)
        }
      }.getOrElse{
        BadRequest("UPLOAD FAILED")
      }
    }


  def follow(userId:String) = SecuredAction{ implicit request =>
    transactional{
      implicit val user = request.user

      User.findById(userId).map { userToBeFollowed =>

        user.toggleFollow(userToBeFollowed)

        Ok(views.html.utils.user.followUserAction(userToBeFollowed))

      }.getOrElse{

        BadRequest

      }
    }
  }

  def favoriteSubDiscipline(subDisciplineId:String) = SecuredAction{ implicit request =>
    transactional{
      implicit val user = request.user

      SubDiscipline.findById(subDisciplineId).map { subDiscipline =>

        val evaluation = SubDisciplineInterestedUser.findByUserAndSubDiscipline(user = user,subDiscipline = subDiscipline)

        if(evaluation.isDefined)
        {
          if(user.currentSubDiscipline == subDiscipline)
          {

            BadRequest(Json.obj("error" -> "Sorry, you cannot un-favorite your current discipline."))
          }else{
            evaluation.get.delete
            Ok(views.html.utils.user.favoriteSubDiscipline(subDiscipline = subDiscipline, pageUser = user))
          }

        }else
        {
          SubDisciplineInterestedUser(user = user,subDiscipline = subDiscipline)
          Ok(views.html.utils.user.favoriteSubDiscipline(subDiscipline = subDiscipline, pageUser = user))
        }

      }.getOrElse{

        BadRequest("Could not process request.")

      }
    }
  }
}
