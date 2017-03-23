package com.github.dnvriend.lifecycle.adapters.services

import akka.NotUsed
import com.github.dnvriend.lifecycle.adapters.repository.PersonRepository
import com.github.dnvriend.lifecycle.api.GreetingService
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.transport.ResponseHeader
import com.lightbend.lagom.scaladsl.server.ServerServiceCall

import scala.concurrent.{ExecutionContext, Future}

class GreetingServiceImpl(repo: PersonRepository)(implicit ec: ExecutionContext) extends GreetingService {
  override def hello(name: String) = ServiceCall { _ =>
    val person = repo.personById(name)
    Future.successful(s"Hello $name with person: $person")
  }

  override def helloWithStatus(status: Int): ServiceCall[NotUsed, NotUsed] = ServerServiceCall { (_, _) =>
    val hdr = ResponseHeader.Ok.withStatus(status)
    Future.successful((hdr, NotUsed))
  }
}
