package controllers

import java.util.UUID

import models.hkg.{HistoryItem, HistoryItemJsonConverter}
import play.api.libs.json.Json
import play.api.mvc._
import service.UserHistoryService

class HistoryController extends Controller with UserHistoryService with HistoryItemJsonConverter {
  protected val emptyList = List[HistoryItem]()

  def getHistoryRest() = Action { request =>
    request.session.get(UUID_KEY) match {
      case Some(uuid) =>
        Ok(Json.toJson(getHistory(uuid)))
      case None =>
        Ok(Json.toJson(emptyList)).withSession(request.session + (UUID_KEY -> UUID.randomUUID().toString))
    }
  }

}
