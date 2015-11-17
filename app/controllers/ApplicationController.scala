package controllers

import javax.inject.Inject

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
  def index = Assets.at(path="/public", "index.html")
}
