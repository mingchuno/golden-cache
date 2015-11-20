package controllers

import javax.inject.Inject

import play.api.i18n.MessagesApi
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._
import service.HKGPostGrabber

class HKGPostController @Inject() (
  val manager: ActorSystemController,
  val messagesApi: MessagesApi)
  extends Controller
  with HKGPostGrabber {

  def getPostRest(messageId: Int, page: Int) = Action.async {
    getPostFromDBOrFallBack(messageId, page).map {
      case Some(post) =>
        Ok(Json.toJson(post))
      case None =>
        NotFound
    } recover {
      case _ =>
        InternalServerError
    }
  }

  def getTopicsRest(page: Int, channel: String) = Action.async {
    getTopis(page, channel).map {
      case Some(topics) =>
        Ok(Json.toJson(topics))
      case None =>
        NotFound
    } recover {
      case _ =>
        InternalServerError
    }
  }

}