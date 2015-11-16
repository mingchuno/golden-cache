package controllers

import javax.inject.Inject

// import com.mohiva.play.silhouette.api.{Environment, LogoutEvent, Silhouette}
// import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
// import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
// import forms._
// import models.User
import play.api.mvc._
import play.api.i18n.MessagesApi

import scala.concurrent.Future

/**
 * The basic application controller.
 *
 * @param messagesApi The Play messages API.
 * @param env The Silhouette environment.
 * @param socialProviderRegistry The social provider registry.
 */
class ApplicationController @Inject() (val messagesApi: MessagesApi) extends Controller {

  /**
   * Handles the index action.
   *
   * @return The result to display.
   */
  def index = Action { implicit request =>
    Ok(views.html.index())
  }
}
