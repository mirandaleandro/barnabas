package models.core

import net.fwbrasil.activate.entity.Entity
import models.PostgresConnection._

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
class IdeaTopic(var idea:Idea, var topic:Topic) extends Entity
{

}

object IdeaTopic
{
  def apply(idea:Idea, topic:Topic):IdeaTopic =
  {
      new IdeaTopic(idea = idea, topic = topic)
  }

  def findByIdea(idea: Idea):List[IdeaTopic] = select[IdeaTopic] where (_.idea :== idea)

  def findByTopic(topic:Topic):List[IdeaTopic] = select[IdeaTopic] where (_.topic :== topic)

}
