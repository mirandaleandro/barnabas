package models.core

import net.fwbrasil.activate.entity.Entity
import models.PostgresConnection._

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

  def findByIdeaAndTopic(idea: Idea, topic:Topic):Option[IdeaTopic] = (select[IdeaTopic] where (_.idea :== idea, _.topic :== topic)).headOption
}
