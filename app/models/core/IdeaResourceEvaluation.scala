package models.core

import models.User
/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */
class IdeaResourceEvaluation(var evaluator:User, var ideaResource:IdeaResource) {

}

object IdeaResourceEvaluation
{
  def apply(evaluator:User, ideaResource:IdeaResource):IdeaResourceEvaluation =
  {
    new IdeaResourceEvaluation(evaluator = evaluator, ideaResource = ideaResource)
  }
}
