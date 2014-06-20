package controllers

import play.api.mvc.{AnyContent, Controller}
import models.core.Interfaces.Searchable
import models.core.{SubDiscipline, ResourceType, Idea}
import models.PostgresConnection._
import net.fwbrasil.activate.statement.query.PaginationNavigator
import play.api.data.Form
import play.api.data.Forms._
import scala.Some
import models.User

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/19/14
 * Time: 12:46 AM
 * To change this template use File | Settings | File Templates.
 */
object Search extends Controller with securesocial.core.SecureSocial
{
  val ITEMS_PER_PAGE = 10

  def search(query:String, page:Int) = SecuredAction{  implicit securedRequest =>
    transactional{
      implicit val user = securedRequest.user
      implicit val request = securedRequest.request
      try
      {

        if(query.isEmpty)
        {
          Ok(views.html.pages.searchResultList(searchResults = List.empty[Idea],currentPageIndex = page,lastPageIndex = 0, numberOfResults = 0, query = query))
        }else{
          val pagination = Idea.search(query,ITEMS_PER_PAGE)

          val ideas: List[Idea] = pagination.page(page-1)

          Ok(views.html.pages.searchResultList(searchResults = ideas,currentPageIndex = page,lastPageIndex = pagination.numberOfPages, numberOfResults =  pagination.numberOfResults, query = query))
        }
      }catch{
        case e:Exception => NotFound(views.html.errors.notFound(request))
      }

    }
  }
  case class QueryForm(var query:String, var page:Option[Int] = None)
  val queryForm = Form(
    mapping(
      "query" -> nonEmptyText,
      "page"  -> optional(number)
    )
      (QueryForm.apply)(QueryForm.unapply)
  )

  def searchByPost = SecuredAction{  implicit securedRequest =>

    queryForm.bindFromRequest.fold(
      formWithErrors => BadRequest("Sorry, no donut for you"),
      (form: QueryForm) => {
          Redirect(routes.Search.search(form.query,form.page.getOrElse(1)))
      })
  }

  def searchableCall(searchable:Searchable) =
  {
    searchable match {
      case(i:Idea) => Some(routes.EvaluateIdeas.evaluateIdeasWithId(i.id))

      case _ => None
    }

  }

  def researchers = {
    User.findAll.sortWith( (x,y) => x.contributions > y.contributions)
  }

  def ideasSortedByFollowers = {
    Idea.findAll.sortBy(_.followersCount)
  }

}
