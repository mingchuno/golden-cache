package models.hkg

import akka.agent.Agent
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object TopicsAgent {
  private val _agent: Agent[Map[String, Topics]] = Agent(Map())

  def get(channel: String): Option[Topics] = {
    _agent.get().get(channel)
  }

  def set(channel: String, topic: Topics) = {
    _agent alter{ old => old + (channel -> topic) }
  }

}