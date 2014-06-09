package models.core

import net.fwbrasil.activate.entity.Entity
import models.User

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
