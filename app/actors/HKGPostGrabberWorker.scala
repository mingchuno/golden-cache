package actors

import actors.HKGPostGrabberWorker.GrabJob
import akka.actor._
import service.HKGPostGrabber

import scala.concurrent.duration._

class HKGPostGrabberWorker extends Actor with ActorLogging with HKGPostGrabber {

  implicit val ec = context.dispatcher

  val parent = context.parent

  override def preStart() = {
    log.info("starting HKGPostGrabberActor")
  }

  def receive = {
    case GrabJob(messageId, page) =>
      log.info(s"receive some job with id $messageId and page $page")
      getPostFromDBOrFallBack(messageId, page).foreach {
        case None =>
          log.warning("cannot found post!")

        case Some(post) =>
          if (post.currentPages < post.totalPages) {
            context.system.scheduler.scheduleOnce(1 second, parent, GrabJob(messageId, page + 1))
          } else {
            log.info("post.currentPages >= post.totalPages and do nothing!")
          }
      }

    case _ =>
  }
}

object HKGPostGrabberWorker {
  def props = Props[HKGPostGrabberWorker]
  case class GrabJob(messageId: Int, page: Int)
}