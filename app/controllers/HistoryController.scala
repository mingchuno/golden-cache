package controllers

import models.hkg.{HistoryItem, HistoryItemJsonConverter}
import play.api.libs.json.Json
import play.api.mvc._
import service.UserHistoryService
import utils.CookieHelper

class HistoryController extends Controller with UserHistoryService with HistoryItemJsonConverter with CookieHelper {
  protected val emptyList = List[HistoryItem]()

  def getHistoryRest() = Action { request =>
    request.cookies.get(UUID_KEY).map(_.value) match {
      case Some(uuid) =>
        Ok(Json.toJson(getHistory(uuid)))
      case None =>
        Ok(Json.toJson(emptyList)).withCookies(genCookie())
    }
  }

  def purgeHistoryRest() = Action { request =>
    request.cookies.get(UUID_KEY).map(_.value) match {
      case Some(uuid) =>
        purgeHistory(uuid)
        Ok
      case None =>
        Ok.withCookies(genCookie())
    }
  }

}
