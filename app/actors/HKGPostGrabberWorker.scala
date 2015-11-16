package actors

import actors.HKGPostGrabberWorker.GrabJob
import akka.actor._
import service.HKGPostGrabber

// import scala.concurrent.ExecutionContext.Implicits.global
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
          // wait some time be4 retry
          log.info(s"[HAHAHA]going to retry with id $messageId and page $page")
          context.system.scheduler.scheduleOnce(30 second, parent, GrabJob(messageId, page))

        case Some(post) =>
          if (post.currentPages < post.totalPages) {
            context.system.scheduler.scheduleOnce(1 second, parent, GrabJob(messageId, page + 1))
          } else {
            log.info(s"going to retry with id $messageId and page $page")
            context.system.scheduler.scheduleOnce(30 second, parent, GrabJob(messageId, page))
          }
      }

    case _ =>
  }
}

object HKGPostGrabberWorker {
  def props = Props[HKGPostGrabberWorker]
  case class GrabJob(messageId: Int, page: Int)
}