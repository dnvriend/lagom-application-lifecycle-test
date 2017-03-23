package com.github.dnvriend.lifecycle.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

trait GreetingService extends Service {

  def hello(name: String): ServiceCall[NotUsed, String]

  override final def descriptor: Descriptor = {
    import Service._
    named("lagom-application-lifecycle-test").withCalls(
      pathCall("/api/hello/:name", hello _),
      pathCall("/api/hello?name", hello _)
    ).withAutoAcl(true)
  }
}

case class GreetingMessage(message: String)
object GreetingMessage {
  implicit val format: Format[GreetingMessage] = Json.format
}
