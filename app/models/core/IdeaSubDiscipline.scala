package models.core

import models.PostgresConnection._
import models.core.SubDiscipline

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/14/14
 * Time: 12:18 AM
 * To change this template use File | Settings | File Templates.
 */

class IdeaSubDiscipline(var idea:Idea, var subDiscipline:SubDiscipline) extends Entity
{

}

object IdeaSubDiscipline
{
  def apply(idea:Idea, subDiscipline:SubDiscipline):IdeaSubDiscipline =
  {
    new IdeaSubDiscipline(idea = idea, subDiscipline = subDiscipline)
  }

  def findByIdea(idea: Idea):List[IdeaSubDiscipline] = select[IdeaSubDiscipline] where (_.idea :== idea)

  def findBySubDiscipline(subDiscipline:SubDiscipline):List[IdeaSubDiscipline] = select[IdeaSubDiscipline] where (_.subDiscipline :== subDiscipline)

  def findByIdeaAndSubDiscipline(idea:Idea, subDiscipline:SubDiscipline):Option[IdeaSubDiscipline] = (select[IdeaSubDiscipline] where (_.subDiscipline :== subDiscipline, _.idea :== idea)).headOption

}

