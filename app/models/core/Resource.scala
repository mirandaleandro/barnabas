package models.core

import net.fwbrasil.activate.entity.Entity
import models.User
import models.PostgresConnection._

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
class Resource(var createdBy:User, var title:String, var url:Option[String], var resourceType:ResourceType) extends Entity
{

}

object Resource
{

  def apply(createdBy:User, title:String, url:Option[String], resourceType:ResourceType):Resource =
  {
     new Resource(createdBy = createdBy, title = title, url, resourceType = resourceType)
  }

  def findById(id: String) = byId[Resource](id)

}

class ResourceType(var createdBy:User, var title:String ) extends Entity
{

}

object ResourceType
{

  def apply(createdBy:User, title:String):ResourceType =
  {
    new ResourceType(createdBy = createdBy, title = title)
  }

  def findAll = all[ResourceType]

  def findById(id: String) = byId[ResourceType](id)

}
