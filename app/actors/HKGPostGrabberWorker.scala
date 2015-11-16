package actors

import actors.HKGPostGrabberWorker.GrabJob
import akka.actor._
import service.HKGPostGrabber

// import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class HKGPostGrabberWorker extends Actor with ActorLogging with HKGPostGrabber {

  implicit val ec = context.dispatcher

  override def preStart() = {
    log.info("starting HKGPostGrabberActor")
  }

  def receive = {
    case GrabJob(messageId, page) =>
      log.info(s"receive some job with id $messageId and page $page")
      grabNewPostAndSave(messageId, page).foreach {
        case None =>
        case Some(post) =>
          if (post.currentPages < post.totalPages) {
            context.system.scheduler.scheduleOnce(1 second, self, GrabJob(messageId, page + 1))
          }
      }
    case _ =>
  }
}

object HKGPostGrabberWorker {
  def props = Props[HKGPostGrabberWorker]
  case class GrabJob(messageId: Int, page: Int)
}