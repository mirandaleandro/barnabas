package models.core

import models.User
import models.PostgresConnection._
/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 2:29 PM
 */
class IdeaResourceEvaluation(var evaluator:User, var ideaResource:IdeaResource) extends Entity{

}

object IdeaResourceEvaluation
{

  def apply(evaluator:User, ideaResource:IdeaResource):IdeaResourceEvaluation =
  {
    new IdeaResourceEvaluation(evaluator = evaluator, ideaResource = ideaResource)
  }

  def findByIdeaResource(ideaResource:IdeaResource):List[IdeaResourceEvaluation] = select[IdeaResourceEvaluation] where(_.ideaResource :== ideaResource)

  def findByUserAndResource(user: User, ideaResource:IdeaResource):Option[IdeaResourceEvaluation] =  (select[IdeaResourceEvaluation] where(_.evaluator :== user, _.ideaResource :== ideaResource)).headOption
}
