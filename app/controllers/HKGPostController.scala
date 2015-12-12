package controllers

import java.util.UUID
import javax.inject.Inject

import play.api.Logger
import play.api.i18n.MessagesApi
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._
import service.{UserHistoryService, HKGPostGrabber}
import utils.LogUtils

class HKGPostController @Inject() (
  val manager: ActorSystemController,
  val messagesApi: MessagesApi)
  extends Controller
  with UserHistoryService
  with HKGPostGrabber {

  def getPostRest(messageId: Int, page: Int) = Action.async { request =>
    getPostFromDBOrFallBack(messageId, page).map {
      case Some(post) =>
        request.session.get(UUID_KEY) match {
          case Some(uuid) =>
            saveHistory(uuid, post.toHistoryItem)
            Ok(Json.toJson(post))
          case None =>
            val newUUID = UUID.randomUUID().toString
            saveHistory(newUUID, post.toHistoryItem)
            Ok(Json.toJson(post))
              .withSession(request.session + (UUID_KEY -> newUUID))
        }
      case None =>
        NotFound
    } recover {
      case e =>
        Logger.warn(LogUtils.getStackTraceAsString(e))
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
      case e =>
        Logger.warn(LogUtils.getStackTraceAsString(e))
        InternalServerError
    }
  }

}