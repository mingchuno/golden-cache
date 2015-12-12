package actors

import akka.actor._
import service.HKGPostGrabber

import scala.concurrent.duration._

/**
 * This actor will receive some message id and decide what to grab
 */
class HKGPostGrabberManager extends Actor with ActorLogging with HKGPostGrabber {
  import HKGPostGrabberManager._

  implicit val ec = context.dispatcher

  val middleMan = context.actorOf(Props[HKGPostGrabberMiddleMan], "hkg-post-grabber-middle-man")

  context.system.scheduler.schedule(2 seconds, 30 seconds, self, BackgroundGrabJob)

  def receive = {
    case GrabJob(messageId) =>
      log.info(s"receive GrabJob:$messageId")
      val page = getPostMaxPage(messageId).getOrElse(1)
      val job = HKGPostGrabberWorker.GrabJob(messageId, page)
      middleMan ! job

    case BackgroundGrabJob =>
      getTopis().onSuccess {
        case None =>
        case Some(topics) =>
          topics.topicList.map(s => GrabJob(s.messageId)).foreach { job =>
            self ! job
          }
      }
  }

}

object HKGPostGrabberManager {
  def props = Props[HKGPostGrabberManager]
  case class GrabJob(messageId: Int)
  case object BackgroundGrabJob
}