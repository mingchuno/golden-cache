package modules

import com.google.inject.AbstractModule
import controllers.ActorSystemController
import net.codingwell.scalaguice.ScalaModule

/**
 * Created by mcor on 11/16/15.
 */
class HKGModule extends AbstractModule with ScalaModule {
  def configure() {
    bind[ActorSystemController]
  }
}
