package controllers

import models.hkg.{HistoryItem, HistoryItemJsonConverter}
import play.api.libs.json.Json
import play.api.mvc._
import service.UserHistoryService
import utils.CookieHelper
import utils.{CookieHelper, LogUtils}

class ApplicationController extends Controller with CookieHelper {

  def postRedirectShortcut(messageId: Int) = Action {
    Redirect(s"/#/post/$messageId/1")
  }

  def loadUser(uuid: String) = Action { request =>
    Redirect("/").withCookies(genCookie(uuid))
  }

  def getUserLink() = Action { request =>
    request.cookies.get(UUID_KEY).map(_.value) match {
      case Some(uuid) =>
        Ok(s"/u/$uuid")
      case None =>
        NotFound
    }
  }
}
