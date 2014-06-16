package models.core

import models.PostgresConnection._

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
class SubDiscipline(var title:String, var template:Template, var discipline:Discipline, var popularity:Long = 0) extends Entity {

  def topics = Topic.findBySubDiscipline(this)

}

object SubDiscipline
{

  def apply(title:String, template:Template, discipline:Discipline, popularity:Long = 0):SubDiscipline =
  {
    new SubDiscipline(title = title, template = template, discipline = discipline, popularity = popularity)
  }

  def defaultSubDiscipline: SubDiscipline = all[SubDiscipline].head

}
