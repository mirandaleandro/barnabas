package models.core

import net.fwbrasil.activate.entity.Entity
import models.User
import models.PostgresConnection._

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */

/*
    popularity is just a measure or a representation of IdeaTopic count for an instance topic. We are doing this to avoid expensive queries.
 */

class Topic(var createdBy:User, var title:String, var subDiscipline:SubDiscipline, var popularity:Long = 0) extends Entity
{

}

object Topic
{
  def apply( createdBy:User, title:String, subDiscipline:SubDiscipline, popularity:Long = 0):Topic =
  {
    new Topic(createdBy = createdBy, title = title, subDiscipline = subDiscipline, popularity = popularity)
  }

  def findBySubDiscipline(subDiscipline: SubDiscipline):List[Topic] = {

    query {
      (topic: Topic) =>
        where(topic.subDiscipline :== subDiscipline) select (topic) orderBy (topic.popularity desc)
    }
  }

  def findBySubDisciplineAndTitle(subDiscipline: SubDiscipline, title:String):Option[Topic] = (select[Topic] where (_.subDiscipline :== subDiscipline, _.title :== title)).headOption

  def findById(id:String):Option[Topic] = byId[Topic](id)
}
