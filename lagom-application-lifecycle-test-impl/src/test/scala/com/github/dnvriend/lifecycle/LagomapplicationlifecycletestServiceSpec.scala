package com.github.dnvriend.lifecycle

import com.lightbend.lagom.scaladsl.server.LocalServiceLocator
import com.lightbend.lagom.scaladsl.testkit.ServiceTest
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers}
import com.github.dnvriend.lagomapplicationlifecycletest.api._
import com.github.dnvriend.lifecycle.api.{GreetingMessage, LagomapplicationlifecycletestService}
import com.github.dnvriend.lifecycle.application.GreetingApplication

class LagomapplicationlifecycletestServiceSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {

  private val server = ServiceTest.startServer(
    ServiceTest.defaultSetup
      .withCassandra(true)
  ) { ctx =>
    new GreetingApplication(ctx) with LocalServiceLocator
  }

  val client = server.serviceClient.implement[LagomapplicationlifecycletestService]

  override protected def afterAll() = server.stop()

  "lagom-application-lifecycle-test service" should {

    "say hello" in {
      client.hello("Alice").invoke().map { answer =>
        answer should ===("Hello, Alice!")
      }
    }

    "allow responding with a custom message" in {
      for {
        _ <- client.useGreeting("Bob").invoke(GreetingMessage("Hi"))
        answer <- client.hello("Bob").invoke()
      } yield {
        answer should ===("Hi, Bob!")
      }
    }
  }
}
