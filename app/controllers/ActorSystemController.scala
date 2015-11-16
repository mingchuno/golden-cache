 package controllers

 import javax.inject._

 import actors.HKGPostGrabberManager
 import akka.actor._

 @Singleton
 class ActorSystemController @Inject() (system: ActorSystem) {

   val postGrabber = system.actorOf(HKGPostGrabberManager.props, "hkg-post-grabber-manager")

 }