package controllers

import play.api.mvc.{AnyContent, Controller}
import models.core.Interfaces.Searchable
import models.core.Idea
import models.PostgresConnection._
import net.fwbrasil.activate.statement.query.PaginationNavigator

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/19/14
 * Time: 12:46 AM
 * To change this template use File | Settings | File Templates.
 */
object Search extends Controller with securesocial.core.SecureSocial
{
  val ITEMS_PER_PAGE = 5

  def search(query:String, page:Int) = SecuredAction{  implicit request =>
    transactional{
      implicit val user = request.user

      try
      {
        val pagination = Idea.search(query,ITEMS_PER_PAGE)

        val ideas: List[Idea] = pagination.page(page-1)

        Ok(views.html.pages.searchResultList(searchResults = ideas,currentPageIndex = page,lastPageIndex = pagination.numberOfPages,query = query))

      }catch{
        case e:Exception => NotFound(views.html.errors.notFound(request.request))
      }

    }
  }

  def searchableCall(searchable:Searchable) =
  {
    searchable match {
      case(i:Idea) => Some(routes.EvaluateIdeas.evaluateIdeasWithId(i.id))

      case _ => None
    }

  }




}
