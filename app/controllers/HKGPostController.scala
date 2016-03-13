package controllers

import java.util.UUID
import javax.inject.Inject

import models.hkg.TopicsAgent
import play.api.Logger
import play.api.i18n.MessagesApi
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._
import service.{UserHistoryService, HKGPostGrabber}
import utils.{CookieHelper, LogUtils}

class HKGPostController @Inject() (
  val manager: ActorSystemController,
  val messagesApi: MessagesApi)
  extends Controller
  with UserHistoryService
  with CookieHelper
  with HKGPostGrabber {

  def getPostRest(messageId: Int, page: Int) = Action.async { request =>
    getPostFromDBOrFallBack(messageId, page).map {
      case Some(post) =>
        request.cookies.get(UUID_KEY).map(_.value) match {
          case Some(uuid) =>
            saveHistory(uuid, post.toHistoryItem)
            Ok(Json.toJson(post))
          case None =>
            val newUUID = UUID.randomUUID().toString
            saveHistory(newUUID, post.toHistoryItem)
            Ok(Json.toJson(post)).withCookies(genCookie(newUUID))
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
        if (page == 1) TopicsAgent.set(channel, topics)
        Ok(Json.toJson(topics))
      case None =>
        if (page == 1) TopicsAgent.get(channel).map(t => Ok(Json.toJson(t))).getOrElse(NotFound)
        else NotFound
    } recover {
      case e =>
        Logger.warn(LogUtils.getStackTraceAsString(e))
        InternalServerError
    }
  }

}
