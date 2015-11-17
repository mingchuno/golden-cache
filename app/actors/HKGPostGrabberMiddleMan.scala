package actors

import akka.actor._
import akka.contrib.throttle.Throttler._
import akka.contrib.throttle._

import scala.concurrent.duration._

class HKGPostGrabberMiddleMan extends Actor with ActorLogging {
  val worker = context.actorOf(HKGPostGrabberWorker.props, "hkg-post-grabber")

  // move to config later
  val throttler = context.actorOf(Props(classOf[TimerBasedThrottler], 1 msgsPer 1.second))
  throttler ! SetTarget(Some(worker))

  def receive = {
    case job: HKGPostGrabberWorker.GrabJob =>
      throttler ! job
  }
}
