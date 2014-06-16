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
    def timestamp = 201406011240l
//    def timestamp = Platform.currentTime

    def up {
        removeAllEntitiesTables
            .ifExists
            .cascade

        createTableForAllEntities.ifNotExists
        createInexistentColumnsForAllEntities

    }
}

class CreateTestData extends Migration {
    def timestamp = 201406011241l
//    def timestamp = Platform.currentTime +100

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
         val subDiscipline = SubDiscipline("Distributed Systems", template, discipline)

         user.currentSubDiscipline = subDiscipline
         user2.currentSubDiscipline = subDiscipline
         user3.currentSubDiscipline = subDiscipline
         user4.currentSubDiscipline = subDiscipline
         user5.currentSubDiscipline = subDiscipline

         val topic1 = Topic(user,"topic 1",subDiscipline = subDiscipline, popularity = 87)
         val topic2 = Topic(user,"topic 2",subDiscipline = subDiscipline, popularity = 54)
         val topic3 = Topic(user,"topic 3",subDiscipline = subDiscipline, popularity = 384)
         val topic5 = Topic(user,"topic 5",subDiscipline = subDiscipline, popularity = 4)
         val topic6 = Topic(user,"topic 6",subDiscipline = subDiscipline, popularity = 68)
         val topic7 = Topic(user,"topic 7",subDiscipline = subDiscipline, popularity = 77)
         val topic8 = Topic(user,"Another topic 1",subDiscipline = subDiscipline, popularity = 7)
         val topic9 = Topic(user,"Another topic 2",subDiscipline = subDiscipline, popularity = 8)
         val topic10 = Topic(user,"Another topic 3",subDiscipline = subDiscipline, popularity = 23)
         val topic11 = Topic(user,"Another topic 4",subDiscipline = subDiscipline, popularity = 13)
         val topic12 = Topic(user,"Another topic 5",subDiscipline = subDiscipline, popularity = 98)
         val topic13 = Topic(user,"Third type topic 1",subDiscipline = subDiscipline, popularity = 87)
         val topic14 = Topic(user,"Third type topic 2",subDiscipline = subDiscipline, popularity = 8)
         val topic15 = Topic(user,"Third type topic 3",subDiscipline = subDiscipline, popularity = 62)
         val topic16 = Topic(user,"Third type topic 4",subDiscipline = subDiscipline, popularity = 24)
         val topic17 = Topic(user,"Third type topic 5",subDiscipline = subDiscipline, popularity = 26)
         val topic18 = Topic(user,"Third type topic 6",subDiscipline = subDiscipline, popularity = 98)
         val topic19 = Topic(user,"Third type topic 7",subDiscipline = subDiscipline, popularity = 46)
         val topic20 = Topic(user,"Third type topic 8",subDiscipline = subDiscipline, popularity = 57)


         val resourceType1 = ResourceType(createdBy = user, "Researcher")
         val resourceType2 = ResourceType(createdBy = user, "Paper")
         val resourceType3 = ResourceType(createdBy = user, "Web Page")
         val resourceType4 = ResourceType(createdBy = user, "Idea")

         val resource1 = Resource(createdBy = user,title = "Thomas Meservy",Some("http://google.com"), resourceType = resourceType1)
         val resource2 = Resource(createdBy = user,title = "State of Art in Distributed Systems",Some("http://google.com"), resourceType = resourceType2)
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

         idea.addSubDiscipline(subDiscipline = subDiscipline)
         idea2.addSubDiscipline(subDiscipline = subDiscipline)
         idea3.addSubDiscipline(subDiscipline = subDiscipline)
         idea4.addSubDiscipline(subDiscipline = subDiscipline)
         idea5.addSubDiscipline(subDiscipline = subDiscipline)
         idea6.addSubDiscipline(subDiscipline = subDiscipline)
         idea7.addSubDiscipline(subDiscipline = subDiscipline)
         idea8.addSubDiscipline(subDiscipline = subDiscipline)
         idea9.addSubDiscipline(subDiscipline = subDiscipline)
         idea10.addSubDiscipline(subDiscipline = subDiscipline)
         idea11.addSubDiscipline(subDiscipline = subDiscipline)
         idea12.addSubDiscipline(subDiscipline = subDiscipline)
         idea13.addSubDiscipline(subDiscipline = subDiscipline)
         idea14.addSubDiscipline(subDiscipline = subDiscipline)
         idea15.addSubDiscipline(subDiscipline = subDiscipline)
         idea16.addSubDiscipline(subDiscipline = subDiscipline)
         idea17.addSubDiscipline(subDiscipline = subDiscipline)
         idea18.addSubDiscipline(subDiscipline = subDiscipline)
         idea19.addSubDiscipline(subDiscipline = subDiscipline)
         idea20.addSubDiscipline(subDiscipline = subDiscipline)
         idea21.addSubDiscipline(subDiscipline = subDiscipline)
         idea22.addSubDiscipline(subDiscipline = subDiscipline)
         idea23.addSubDiscipline(subDiscipline = subDiscipline)
         idea24.addSubDiscipline(subDiscipline = subDiscipline)
         idea25.addSubDiscipline(subDiscipline = subDiscipline)

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

         val ideaUser = IdeaUser(user = user, idea = idea)
         ideaUser.likeIdea()
         ideaUser.followIdea()
         ideaUser.interestedInCollaborateWithIdea()

         val ideaUser1 = IdeaUser(user = user1, idea = idea)
         ideaUser1.likeIdea()
         ideaUser1.followIdea()
         ideaUser1.interestedInCollaborateWithIdea()

         val ideaUser2 = IdeaUser(user = user2, idea = idea)
         ideaUser2.likeIdea()
         ideaUser2.followIdea()
         ideaUser2.interestedInCollaborateWithIdea()

         val ideaUser3 = IdeaUser(user = user3, idea = idea)
         ideaUser3.likeIdea()
         ideaUser3.followIdea()
         ideaUser3.interestedInCollaborateWithIdea()

         val ideaUser4 = IdeaUser(user = user4, idea = idea)
         ideaUser4.likeIdea()
         ideaUser4.followIdea()
         ideaUser4.interestedInCollaborateWithIdea()

         val ideaUser5 = IdeaUser(user = user5, idea = idea)
         ideaUser5.likeIdea()
         ideaUser5.followIdea()
         ideaUser5.interestedInCollaborateWithIdea()

       }



    }
}
