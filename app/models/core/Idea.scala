package models.core

import Interfaces.Searchable
import net.fwbrasil.activate.entity.Entity
import models.User
import models.PostgresConnection._
import controllers.routes
import net.fwbrasil.activate.statement.query.PaginationNavigator

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
           var votedUp:Long = 0,
           var followersCount:Long = 0,
           var collaboratorsCount:Long = 0) extends Entity with Searchable
{

  def authors:List[User] = ???  //we will probably wish to have coauthoring of ideas. By now we will just use "createdBy"
  def followers:List[User] = ???
  def interestedUsers:List[User] = ???

  def resources:List[IdeaResource] = IdeaResource.findByIdea(this)

  def topics:List[Topic] = IdeaTopic.findByIdea(this).map(_.topic)

  def subDisciplines:List[SubDiscipline] = ???

  def phase = this.ideaPhase.title

  def votedDown = voted - votedUp

  def feedbackRatio:Float = {
    if (this.visited == 0 || this.voted == 0)
      0
    else
      this.voted/this.visited
  }

  def votedUpRatio:Double = {
    if (this.votedUp == 0 || this.voted == 0)
      0
    else
      this.votedUp.toDouble / this.voted
  }

  def addTopic(topic:Topic)
  {
    if(!IdeaTopic.findByIdeaAndTopic(idea = this, topic = topic).isDefined)
    {
      IdeaTopic( idea = this, topic = topic)

      topic.popularity += 1
    }
  }

  def addSubDiscipline(subDiscipline:SubDiscipline) {

    if(!IdeaSubDiscipline.findByIdeaAndSubDiscipline(idea = this, subDiscipline = subDiscipline).isDefined)
    {
      IdeaSubDiscipline( idea = this, subDiscipline = subDiscipline)

      subDiscipline.popularity += 1
    }
  }

  def addResource(resource:Resource, suggestedBy:User) {

    if(!IdeaResource.findByIdeaAndResource(idea = this, resource = resource).isDefined)
    {
      IdeaResource( idea = this, suggestedBy = suggestedBy, resource = resource)
    }
  }

  def searchTitle =  title

  def searchDescription:String = {
    val escapedDescription = description.replaceAll("""<(?!\/?a(?=>|\s.*>))\/?.*?>""", "")
    escapedDescription.substring(0,Math.min(escapedDescription.length, 300 ))
  }

}

object Idea
{
//  def search(query: String):List[Idea] =
//  {
//    select[Idea] where( idea => (idea.title like query) :|| (idea.description like query) )
//  }
//
  def search(query:String, itemsPerPage:Int ) = paginatedQuery {
    (entity: Idea) =>
      where(entity.title like "*"+query+"*") select (entity) orderBy (entity.title)
    }.navigator(itemsPerPage)



  def apply(createdBy:User):Idea =
  {
      Idea(createdBy,"","", IdeaPhase.Inception)
  }

  def apply(createdBy:User, title:String, description:String, ideaPhase:IdeaPhase):Idea = Idea(createdBy,title,description,ideaPhase,0)

  def apply(createdBy:User, title:String, description:String, ideaPhase:IdeaPhase, visited:Long = 0):Idea =
  {
    new Idea(createdBy = createdBy, title = title, description = description, ideaPhase = ideaPhase, visited = visited)
  }

  def ideaFromUser(user:User):List[Idea] = select[Idea] where( _.createdBy :== user )

  def ideaAtPhase(ideaPhase:IdeaPhase):List[Idea] = select[Idea] where( _.ideaPhase :== ideaPhase )

  def findById(id:String):Option[Idea] = byId[Idea](id)

  def ideaForEvaluation(user:User): Option[Idea] =
  {
    val ideasForSubDiscipline: List[Idea] = IdeaSubDiscipline.findBySubDiscipline(subDiscipline = user.currentSubDiscipline).map(_.idea)
    val ideasEvaluatedByUser: List[Idea] = IdeaUser.findByUser(user = user).map(_.idea)
    ideasForSubDiscipline.filterNot(ideasEvaluatedByUser.toSet).headOption
  }
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
