package service

import dao.hkg.PostCollection
import models.hkg._
import utils.HKGApi2

import play.api.Logger
import play.api.Play.current
import play.api.libs.json.{JsError, JsSuccess}
import play.api.libs.ws._
import play.api.libs.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

trait HKGPostGrabber extends GoldenPostJsonConverter with PostCollection {

  def getTopis(page: Int = 1, channel: String = "BW"): Future[Option[Topics]] = {
    val filter = if (channel == "BW") "Y" else "N"
    val sensor = "N"

    WS.url(HKGApi2.getTopicUrl(channel, page, filter, sensor))
      .get().map { response =>
      val json = response.json
      if ((json \ "success").as[Boolean]) {
        json.validate[Topics] match {
          case JsSuccess(topics, _) =>
            Some(topics)
          case JsError(err) =>
            err.foreach { e => Logger.warn(e.toString) }
            None
        }
        // need to save the post later!
      } else {
        None
      }
    }
  }

  private def grabNewPost(messageId: Int, page: Int): Future[Option[Post]] = {
    Logger.debug(s"grabNewPost: $messageId and page $page")
    val filter = "N"
    val sensor = "N"
    val key = HKGApi2.getPostHash(messageId, (page-1) * 25, 0, filter, sensor)

    val data = Map(
      "filtermode" -> Seq(filter),
      "sensormode" -> Seq(sensor),
      "s" -> Seq(key),
      "returntype" -> Seq("json"),
      "limit" -> Seq(26.toString),
      "start" -> Seq(((page-1) * 25).toString),
      "message" -> Seq(messageId.toString),
      "user_id" -> Seq(0.toString)
    )

    WS.url(HKGApi2.getPostUrl())
      .post(data)
      .map { response =>

        val json = response.json
        if ((json \ "success").as[Boolean]) {
          json.validate[Post] match {
            case JsSuccess(post, _) =>
              Some(post)
            case JsError(err) =>
              err.foreach { e => Logger.warn(e.toString) }
              None
          }
          // need to save the post later!
        } else {
          None
        }
    }
  } // end grabNewPost

  // apply side effect
  def grabNewPostAndSave(messageId: Int, page: Int): Future[Option[Post]] = {
    Logger.info(s"going to grab new post $messageId and page $page")
    grabNewPost(messageId, page).andThen {
      case Success(Some(post)) =>
        // fix golden api currentPages not work
        upsertPost(post.copy(currentPages = page))
      case Success(None) =>
        Logger.info(s"cannot load this post $messageId and page $page")
      case Failure(e) =>
        Logger.info(s"internal error or api no respond for $messageId and page $page")
    }
  }

  def getPostFromDBOrFallBack(messageId: Int, page: Int): Future[Option[Post]] = {
    getPostByIdAndPage(messageId, page) match {
      case Some(post) if post.currentPages < post.totalPages =>
        // must be history
        Future.successful(Some(post))

      case Some(post) if post.totalReplies % 25 == 0 =>
        // just become history
        Future.successful(Some(post))

      case Some(post) if post.currentPages >= 41 =>
        // in the end
        Future.successful(Some(post))

      case Some(_) =>
        // this page is not history yet, so we grab
        grabNewPostAndSave(messageId, page)

      case None =>
        // not found, of coz grab
        grabNewPostAndSave(messageId, page)
    }
  }

}
