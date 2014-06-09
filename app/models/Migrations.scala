package models

import net.fwbrasil.activate.migration.Migration
import models.PostgresConnection._
import securesocialpersistence.{UserIdentity, UserAuthenticationMethod}
import xml.dtd.SystemID
import compat.Platform
import securesocial.core._
import securesocial.core.providers.utils
import utils.BCryptPasswordHasher
import com.jolbox.bonecp.UsernamePassword
import models.core._

/**
 * Created with IntelliJ IDEA.
 * Usuario: felipe
 * Date: 7/8/12
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */

class CreateSchema extends Migration {
    def timestamp = Platform.currentTime//201406011240l

    def up {
        removeAllEntitiesTables
            .ifExists
            .cascade

        createTableForAllEntities.ifNotExists
        createInexistentColumnsForAllEntities

    }
}

class CreateData extends Migration {
    def timestamp = Platform.currentTime//201406011240l

    def up {
       customScript
       {
         val socialUser1 = SocialUser(IdentityId("mirandaleandro@gmail.com","userpass"),
           "Azul",
           "Azul Negro Vermelho",
           "Vermelho",
           Some("mirandaleandro@gmail.com"),
           Some("http://www.gravatar.com/avatar/da4550c6fcd6d5d4f880755587b995ac?d=404"),
           AuthenticationMethod("userPassword"),
           None,
           None,
           Some(Registry.hashers.currentHasher.hash("socrates")))

         val socialUser2 = SocialUser(IdentityId("mirandaleandro@gmail.com","userpass"),
           "Luiz",
           "Luiz Inácio Silva",
           "Silva",
           Some("lulasilva@gmail.com"),
           Some("http://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Lula_-_foto_oficial05012007_edit.jpg/220px-Lula_-_foto_oficial05012007_edit.jpg"),
           AuthenticationMethod("userPassword"),
           None,
           None,
           Some(Registry.hashers.currentHasher.hash("socrates")))

         val user = User()
         val user2 = User()

         val identity1 = UserIdentity(user,socialUser1)
         val identity2 = UserIdentity(user2,socialUser2)
         val template = Template(title=true,description = true,topics = true)

         val discipline = Discipline("Information Systems",user)
         val subDiscipline = SubDiscipline("Distributed Systems", template = template, discipline)

         val topic1 = Topic(user,"topic 1",subDiscipline = subDiscipline)
         val topic2 = Topic(user,"topic 2",subDiscipline = subDiscipline)
         val topic3 = Topic(user,"topic 3",subDiscipline = subDiscipline)
         val topic4 = Topic(user,"topic 4",subDiscipline = subDiscipline)
         val topic5 = Topic(user,"topic 5",subDiscipline = subDiscipline)
         val topic6 = Topic(user,"topic 6",subDiscipline = subDiscipline)
         val topic7 = Topic(user,"topic 7",subDiscipline = subDiscipline)


         val resourceType1 = ResourceType(createdBy = user, "Researcher")
         val resourceType2 = ResourceType(createdBy = user, "Paper")
         val resourceType3 = ResourceType(createdBy = user, "Web Page")
         val resourceType4 = ResourceType(createdBy = user, "Idea")

         val resource1 = Resource(createdBy = user,title = "Thomas Meservy",Some("httpL//google.com"), resourceType = resourceType1)
         val resource2 = Resource(createdBy = user,title = "State of Art in Distributed Systems",Some("httpL//google.com"), resourceType = resourceType2)
         val resource3 = Resource(createdBy = user,title = "Cuda", None, resourceType = resourceType1)
         val resource4 = Resource(createdBy = user,title = "Thomas Meservy", None, resourceType = resourceType1)

         val ideaPhase1 = IdeaPhase(user,"Inception")
         val ideaPhase2 = IdeaPhase(user,"Data Analysis")
         val ideaPhase3 = IdeaPhase(user,"Article")


         val idea = Idea(createdBy = user, "IdeationLab: a collaborative for researches to discuss and obtaing feedback on ideas ","Idea Description", ideaPhase = ideaPhase1)
         val idea2 = Idea(createdBy = user, "Super Idea Title goes here ","Lorem Ipsum", ideaPhase = ideaPhase2)
         val idea3 = Idea(createdBy = user, "Super Idea Title goes here ","Lorem Ipsum", ideaPhase = ideaPhase3)
         val idea4 = Idea(createdBy = user, "Super Idea Title goes here ","Lorem Ipsum", ideaPhase = ideaPhase1)
         val idea5 = Idea(createdBy = user2, "Super Idea Title goes here ","Lorem Ipsum", ideaPhase = ideaPhase2)
         val idea6 = Idea(createdBy = user2, "Super Idea Title goes here ","Lorem Ipsum", ideaPhase = ideaPhase3)
         val idea7 = Idea(createdBy = user2, "Super Idea Title goes here ","Lorem Ipsum", ideaPhase = ideaPhase1)

         val ideaResource1 = IdeaResource(suggestedBy = user,idea = idea, resource = resource1)
         val ideaResource2 = IdeaResource(suggestedBy = user,idea = idea, resource = resource2)
         val ideaResource3 = IdeaResource(suggestedBy = user,idea = idea, resource = resource3)
         val ideaResource4 = IdeaResource(suggestedBy = user,idea = idea, resource = resource4)
         val ideaResource5 = IdeaResource(suggestedBy = user2,idea = idea2, resource = resource3)
         val ideaResource6 = IdeaResource(suggestedBy = user2,idea = idea2, resource = resource4)

         
         val ideaResourceEvaluation1 = IdeaResourceEvaluation(evaluator = user, ideaResource1) 
         val ideaResourceEvaluation2 = IdeaResourceEvaluation(evaluator = user, ideaResource2) 
         val ideaResourceEvaluation3 = IdeaResourceEvaluation(evaluator = user, ideaResource3) 
         val ideaResourceEvaluation4 = IdeaResourceEvaluation(evaluator = user, ideaResource4) 
         val ideaResourceEvaluation5 = IdeaResourceEvaluation(evaluator = user2, ideaResource1) 
         val ideaResourceEvaluation6 = IdeaResourceEvaluation(evaluator = user2, ideaResource2) 
         val ideaResourceEvaluation7 = IdeaResourceEvaluation(evaluator = user2, ideaResource3) 
         val ideaResourceEvaluation8 = IdeaResourceEvaluation(evaluator = user2, ideaResource4) 

       }



    }
}
