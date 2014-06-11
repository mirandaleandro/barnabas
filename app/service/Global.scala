package service

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 5/4/14
 * Time: 12:37 AM
 * To change this template use File | Settings | File Templates.
 */
import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import concurrent.Future
import securesocial.core.SecuredRequest

object Global extends GlobalSettings {

  override def onHandlerNotFound(request: RequestHeader): Future[SimpleResult] = {
    Future.successful(NotFound(
      views.html.errors.notFound(request)( request.asInstanceOf[SecuredRequest[Any]].user)
    ))
  }

}