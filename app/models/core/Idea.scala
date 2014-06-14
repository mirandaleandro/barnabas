package models.core

import net.fwbrasil.activate.entity.Entity
import models.User
import models.PostgresConnection._

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/8/14
 * Time: 1:54 PM
 */
class Idea(var createdBy:User,
           var title:String,
           var description:String,
           var ideaPhase:IdeaPhase,
           var visited:Long = 0,
           var voted:Long = 0,
           var followersCount:Long = 0,
           var collaboratorsCount:Long = 0) extends Entity
{

  def authors:List[User] = ???  //we will probably wish to have coauthoring of ideas. By now we will just use "createdBy"
  def followers:List[User] = ???
  def interestedUsers:List[User] = ???
  def resources:List[Resource] = ???
  def topics:List[Topic] = ???
  def subDisciplines:List[SubDiscipline] = ???

  def phase = this.ideaPhase.title

  def feedbackRatio:Float = {
    if (this.visited == 0 || this.voted == 0)
      0
    else
      this.voted/this.visited
  }

  def addTopic(topic:Topic) {
    IdeaTopic( idea = this, topic = topic)
    topic.popularity+=1
  }

  def addSubDiscipline(subDiscipline:SubDiscipline) {
    IdeaSubDiscipline( idea = this, subDiscipline = subDiscipline)
    subDiscipline.popularity+=1
  }
}

object Idea
{
  def apply(createdBy:User, title:String, description:String, ideaPhase:IdeaPhase):Idea = Idea(createdBy,title,description,ideaPhase,0)

  def apply(createdBy:User, title:String, description:String, ideaPhase:IdeaPhase, visited:Long = 0):Idea =
  {
    new Idea(createdBy = createdBy, title = title, description = description, ideaPhase = ideaPhase, visited = visited)
  }

  def ideaFromUser(user:User):List[Idea] = select[Idea] where( _.createdBy :== user )

  def ideaAtPhase(ideaPhase:IdeaPhase):List[Idea] = select[Idea] where( _.ideaPhase :== ideaPhase )

  def findById(id:String):Option[Idea] = byId[Idea](id)
}

class IdeaPhase(var createdBy:User, var title:String ) extends Entity
{

}

object IdeaPhase
{



  def apply(createdBy:User, title:String):IdeaPhase =
  {
    new IdeaPhase(createdBy = createdBy, title = title)
  }

  def findAll = transactional{all[IdeaPhase]}

  def findIdeaByTitle(title:String) = transactional{
    (select[IdeaPhase] where(_.title :== title)).headOption
  }

  def findIdeaByTitleForce(title:String) = findIdeaByTitle(title).get

  def Inception = findIdeaByTitleForce("Inception")
  def DataAnalysis = findIdeaByTitleForce("DataAnalysis")
  def Article = findIdeaByTitleForce("Article")


}
