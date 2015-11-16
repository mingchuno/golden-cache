import actors.HKGPostGrabberWorker
import play.api.Play.current
import play.api._
import play.api.libs.concurrent.Akka

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    // Akka.system.actorOf(HKGPostGrabberWorker.props, "hkg-post-grabber")
  }
}