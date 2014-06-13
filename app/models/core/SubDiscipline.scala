package models.core

import net.fwbrasil.activate.entity.Entity

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
class SubDiscipline(var title:String, var template:Template, var discipline:Discipline) extends Entity {

  def topics = Topic.findBySubDiscipline(this)

}

object SubDiscipline
{
  def apply(title:String, template:Template, discipline:Discipline):SubDiscipline =
  {
    new SubDiscipline(title = title, template = template, discipline = discipline)
  }
}
