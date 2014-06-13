package controllers

import play.api.mvc.{Action, Controller}
import util.Random

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 6/11/14
 * Time: 12:11 AM
 * To change this template use File | Settings | File Templates.
 */
object SubmitIdea extends Controller with securesocial.core.SecureSocial
{
  val IMAGE_STORAGE_FOLDER = "public/images/richtext/"
  val IMAGE_ASSETS_FOLDER = "images/richtext/"


  def richTextImageUpload = Action(parse.multipartFormData) { request =>
    request.body.file("file").map { picture =>
      val filename = CustomRandom.nextString(28)

      val path = String.format("%s%s",IMAGE_STORAGE_FOLDER, filename)

      val file =new java.io.File(path)

      picture.ref.moveTo(file)

      Ok(routes.Assets.at(IMAGE_ASSETS_FOLDER + filename).url)

    }.getOrElse {
      BadRequest("UPLOAD FAILED")
    }
  }

}

object CustomRandom
{
  val alphanumericCharSet = (('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')).toList

  def nextString(length: Int): String = {
    randomStringFromCharList(length, alphanumericCharSet)
  }

  def randomStringFromCharList(length: Int, chars: Seq[Char]): String = {
    val sb = new StringBuilder
    for (i <- 1 to length) {
      val randomNum = Random.nextInt(chars.length)
      sb.append(chars(randomNum))
    }
    sb.toString()
  }
}