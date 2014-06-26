package models.core.Interfaces

import models.User
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/19/14
 * Time: 12:27 AM
 * To change this template use File | Settings | File Templates.
 */
trait Searchable {

  def searchTitle:String
  def searchDescription:String
  def searchAuthors:List[User]
  def searchCreationDate:Date
}
