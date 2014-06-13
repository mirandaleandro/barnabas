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
         val socialUser = SocialUser(IdentityId("mirandaleandro@gmail.com","userpass"),
           "Leandro",
           "Miranda",
           "Leandro Gomes de Miranda",
           Some("mirandaleandro@gmail.com"),
           Some("http://www.gravatar.com/avatar/da4550c6fcd6d5d4f880755587b995ac?d=404"),
           AuthenticationMethod("userPassword"),
           None,
           None,
           Some(Registry.hashers.currentHasher.hash("socrates")))

         val socialUser1 = SocialUser(IdentityId("user1@gmail.com","userpass"),
           "Luiz",
           "Silva",
           "Luiz Inácio Silva",
           Some("user1@gmail.com"),
           Some("http://www.thewashingtonnote.com/twn_up_fls/Lula2RT.jpg"),
           AuthenticationMethod("userPassword"),
           None,
           None,
           Some(Registry.hashers.currentHasher.hash("socrates")))

         val socialUser2 = SocialUser(IdentityId("user2@gmail.com","userpass"),
           "Dilma",
           "Rousseff",
           "Dilma Rousseff",
           Some("user2@gmail.com"),
           Some("http://www.agoravocepode.com.br/bloggeoimob/wp-content/uploads/2012/12/DILMA-ROUSSEFF.jpg"),
           AuthenticationMethod("userPassword"),
           None,
           None,
           Some(Registry.hashers.currentHasher.hash("socrates")))

         val socialUser3 = SocialUser(IdentityId("user3@gmail.com","userpass"),
           "Vladimir",
           "Putin",
           "Vladimir Putin",
           Some("user3@gmail.com"),
           Some("http://upload.wikimedia.org/wikipedia/commons/d/d1/Vladimir_Putin_12020.jpg"),
           AuthenticationMethod("userPassword"),
           None,
           None,
           Some(Registry.hashers.currentHasher.hash("socrates")))

         val socialUser4 = SocialUser(IdentityId("user4@gmail.com","userpass"),
           "Barack",
           "Obama",
           "Barack Obama",
           Some("user4@gmail.com"),
           Some("http://media.washingtonpost.com/wp-srv/politics/congress/members/photos/228/O000167.jpg"),
           AuthenticationMethod("userPassword"),
           None,
           None,
           Some(Registry.hashers.currentHasher.hash("socrates")))

         val socialUser5 = SocialUser(IdentityId("user5@gmail.com","userpass"),
           "George",
           "Bush",
           "George W. Bush",
           Some("user5@gmail.com"),
           Some("http://www.voltairenet.org/local/cache-vignettes/L300xH300/auton5238-bf2a7.jpg"),
           AuthenticationMethod("userPassword"),
           None,
           None,
           Some(Registry.hashers.currentHasher.hash("socrates")))

         val user = User()
         val user1 = User()
         val user2 = User()
         val user3 = User()
         val user4 = User()
         val user5 = User()

         val identity = UserIdentity(user,socialUser)
         val identity1 = UserIdentity(user1,socialUser1)
         val identity2 = UserIdentity(user2,socialUser2)
         val identity3 = UserIdentity(user3,socialUser3)
         val identity4 = UserIdentity(user4,socialUser4)
         val identity5 = UserIdentity(user5,socialUser5)

         val template = Template(title=true,description = true,topics = true)
         val discipline = Discipline("Information Systems",user)
         val subDiscipline = SubDiscipline("Distributed Systems", template = template, discipline)

         user.currentSubDiscipline = subDiscipline
         user2.currentSubDiscipline = subDiscipline
         user3.currentSubDiscipline = subDiscipline

         val topic1 = Topic(user,"topic 1",subDiscipline = subDiscipline, popularity = 1)
         val topic2 = Topic(user,"topic 2",subDiscipline = subDiscipline, popularity = 2)
         val topic3 = Topic(user,"topic 3",subDiscipline = subDiscipline, popularity = 3)
         val topic4 = Topic(user,"topic 4",subDiscipline = subDiscipline, popularity = 4)
         val topic5 = Topic(user,"topic 5",subDiscipline = subDiscipline, popularity = 5)
         val topic6 = Topic(user,"topic 6",subDiscipline = subDiscipline, popularity = 6)
         val topic7 = Topic(user,"topic 7",subDiscipline = subDiscipline, popularity = 7)


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


         val idea = Idea(createdBy = user, "IdeationLab: a collaborative for researches to discuss and obtaing feedback on ideas ","Idea Description", ideaPhase = ideaPhase1, visited = 10)
         val idea2 = Idea(createdBy = user, "Super Idea Title goes here 2 ","Lorem Ipsum", ideaPhase = ideaPhase2, visited = 10)
         val idea3 = Idea(createdBy = user, "Super Idea Title goes here 3","Lorem Ipsum", ideaPhase = ideaPhase3, visited = 10)
         val idea4 = Idea(createdBy = user, "Super Idea Title goes here 4","Lorem Ipsum", ideaPhase = ideaPhase1, visited = 10)
         val idea5 = Idea(createdBy = user2, "Super Idea Title goes here 5","Lorem Ipsum", ideaPhase = ideaPhase2)
         val idea6 = Idea(createdBy = user2, "Super Idea Title goes here 6","Lorem Ipsum", ideaPhase = ideaPhase3)
         val idea7 = Idea(createdBy = user2, "Super Idea Title goes here 7","Lorem Ipsum", ideaPhase = ideaPhase1)

         val idea8 = Idea(createdBy = user, "Super Idea Title goes here 8 ","Lorem Ipsum", ideaPhase = ideaPhase2)
         val idea9 = Idea(createdBy = user, "Super Idea Title goes here 9","Lorem Ipsum", ideaPhase = ideaPhase3)
         val idea10 = Idea(createdBy = user, "Super Idea Title goes here 10","Lorem Ipsum", ideaPhase = ideaPhase1)
         val idea11 = Idea(createdBy = user2, "Super Idea Title goes here 11","Lorem Ipsum", ideaPhase = ideaPhase2)
         val idea12 = Idea(createdBy = user2, "Super Idea Title goes here 12","Lorem Ipsum", ideaPhase = ideaPhase3)
         val idea13 = Idea(createdBy = user2, "Super Idea Title goes here 13","Lorem Ipsum", ideaPhase = ideaPhase1)

         val idea14 = Idea(createdBy = user, "Super Idea Title goes here 14 ","Lorem Ipsum", ideaPhase = ideaPhase2)
         val idea15 = Idea(createdBy = user, "Super Idea Title goes here 15","Lorem Ipsum", ideaPhase = ideaPhase3)
         val idea16 = Idea(createdBy = user, "Super Idea Title goes here 16","Lorem Ipsum", ideaPhase = ideaPhase1)
         val idea17 = Idea(createdBy = user2, "Super Idea Title goes here 17","Lorem Ipsum", ideaPhase = ideaPhase2)
         val idea18 = Idea(createdBy = user2, "Super Idea Title goes here 18","Lorem Ipsum", ideaPhase = ideaPhase3)
         val idea19 = Idea(createdBy = user2, "Super Idea Title goes here 19","Lorem Ipsum", ideaPhase = ideaPhase1)

         val idea20 = Idea(createdBy = user, "Super Idea Title goes here 20 ","Lorem Ipsum", ideaPhase = ideaPhase2)
         val idea21 = Idea(createdBy = user, "Super Idea Title goes here 21","Lorem Ipsum", ideaPhase = ideaPhase3)
         val idea22 = Idea(createdBy = user, "Super Idea Title goes here 22","Lorem Ipsum", ideaPhase = ideaPhase1)
         val idea23 = Idea(createdBy = user2, "Super Idea Title goes here 23","Lorem Ipsum", ideaPhase = ideaPhase2)
         val idea24 = Idea(createdBy = user2, "Super Idea Title goes here 24","Lorem Ipsum", ideaPhase = ideaPhase3)
         val idea25 = Idea(createdBy = user2, "Super Idea Title goes here 25","Lorem Ipsum", ideaPhase = ideaPhase1)

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


         val ideaFollower1 = IdeaFollower(follower = user,idea = idea)
         val ideaFollower2 = IdeaFollower(follower = user1,idea = idea)
         val ideaFollower3 = IdeaFollower(follower = user2,idea = idea)
         val ideaFollower4 = IdeaFollower(follower = user3,idea = idea)
         val ideaFollower5 = IdeaFollower(follower = user4,idea = idea)
         val ideaFollower6 = IdeaFollower(follower = user5,idea = idea)

         val ideaFollower7  = IdeaFollower(follower = user,idea = idea12)
         val ideaFollower8  = IdeaFollower(follower = user1,idea = idea2)
         val ideaFollower9  = IdeaFollower(follower = user2,idea = idea2)
         val ideaFollower10 = IdeaFollower(follower = user3,idea = idea2)
         val ideaFollower11 = IdeaFollower(follower = user4,idea = idea2)
         val ideaFollower12 = IdeaFollower(follower = user5,idea = idea2)

         val ideaInterestedUser1 = IdeaInterestedUser(interestedUser = user,idea = idea)
         val ideaInterestedUser2 = IdeaInterestedUser(interestedUser = user1,idea = idea)
         val ideaInterestedUser3 = IdeaInterestedUser(interestedUser = user2,idea = idea)
         val ideaInterestedUser4 = IdeaInterestedUser(interestedUser = user3,idea = idea)
         val ideaInterestedUser5 = IdeaInterestedUser(interestedUser = user4,idea = idea)
         val ideaInterestedUser6 = IdeaInterestedUser(interestedUser = user5,idea = idea)

         val ideaInterestedUser7  = IdeaInterestedUser(interestedUser = user,idea = idea12)
         val ideaInterestedUser8  = IdeaInterestedUser(interestedUser = user1,idea = idea2)
         val ideaInterestedUser9  = IdeaInterestedUser(interestedUser = user2,idea = idea2)
         val ideaInterestedUser10 = IdeaInterestedUser(interestedUser = user3,idea = idea2)
         val ideaInterestedUser11 = IdeaInterestedUser(interestedUser = user4,idea = idea2)
         val ideaInterestedUser12 = IdeaInterestedUser(interestedUser = user5,idea = idea2)


       }



    }
}
