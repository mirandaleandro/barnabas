package service


import play.api.mvc.{AnyContent, Controller, RequestHeader, Request}
import play.api.templates.{Html, Txt}
import play.api.{Logger, Plugin, Application}
import securesocial.core.{Identity, SecuredRequest}
import play.api.data.Form
import securesocial.controllers.Registration.RegistrationInfo
import securesocial.controllers.PasswordChange.ChangeInfo
import securesocial.controllers.TemplatesPlugin
import views.html.helper.form

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 5/2/14
 * Time: 11:01 PM
 * To change this template use File | Settings | File Templates.
 */

class CustomSecureSocialViews(application: Application) extends TemplatesPlugin {


  def getLoginPage[A](implicit request: play.api.mvc.Request[A],form: play.api.data.Form[(String, String)], msg: Option[String]): play.api.templates.Html =
  {
    views.html.customsecuresocial.login(form,msg)
  }


  def getNotAuthorizedPage[A](implicit request: play.api.mvc.Request[A]): play.api.templates.Html = ???
 
  def getPasswordChangePage[A](implicit request: securesocial.core.SecuredRequest[A],form: play.api.data.Form[securesocial.controllers.PasswordChange.ChangeInfo]): play.api.templates.Html = ???
  def getResetPasswordPage[A](implicit request: play.api.mvc.Request[A],form: play.api.data.Form[(String, String)], token: String): play.api.templates.Html = ???
  def getSignUpPage[A](implicit request: play.api.mvc.Request[A],form: play.api.data.Form[securesocial.controllers.Registration.RegistrationInfo], token: String): play.api.templates.Html = ???
  def getStartResetPasswordPage[A](implicit request: play.api.mvc.Request[A],form: play.api.data.Form[String]): play.api.templates.Html = ???
  def getStartSignUpPage[A](implicit request: play.api.mvc.Request[A],form: play.api.data.Form[String]): play.api.templates.Html = ???
  def getUnknownEmailNotice()(implicit request: play.api.mvc.RequestHeader): (Option[play.api.templates.Txt], Option[play.api.templates.Html]) = ???

  def getAlreadyRegisteredEmail(user: securesocial.core.Identity)(implicit request: play.api.mvc.RequestHeader): (Option[play.api.templates.Txt], Option[play.api.templates.Html]) = ???
  def getPasswordChangedNoticeEmail(user: securesocial.core.Identity)(implicit request: play.api.mvc.RequestHeader): (Option[play.api.templates.Txt], Option[play.api.templates.Html]) = ???
  def getSendPasswordResetEmail(user: securesocial.core.Identity,token: String)(implicit request: play.api.mvc.RequestHeader): (Option[play.api.templates.Txt], Option[play.api.templates.Html]) = ???
  def getSignUpEmail(token: String)(implicit request: play.api.mvc.RequestHeader): (Option[play.api.templates.Txt], Option[play.api.templates.Html]) = ???
  def getWelcomeEmail(user: securesocial.core.Identity)(implicit request: play.api.mvc.RequestHeader): (Option[play.api.templates.Txt], Option[play.api.templates.Html]) = ???



}