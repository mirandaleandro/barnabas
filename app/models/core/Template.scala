package models.core

import net.fwbrasil.activate.entity.Entity

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 2:04 PM
 */

//This is still cloudy. Must to be refactored, updated or removed in the future
class Template(var title:Boolean,var description:Boolean, var topics:Boolean) extends Entity
{

}
object Template
{
  def apply(title:Boolean, description:Boolean, topics:Boolean):Template =
  {
     new Template(title = title, description = description, topics = topics)
  }
}

