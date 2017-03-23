package com.github.dnvriend.lifecycle.adapters.services

import com.github.dnvriend.lifecycle.adapters.repository.PersonRepository
import com.github.dnvriend.lifecycle.api.GreetingService
import com.lightbend.lagom.scaladsl.api.ServiceCall

import scala.concurrent.{ExecutionContext, Future}

class GreetingServiceImpl(repo: PersonRepository)(implicit ec: ExecutionContext) extends GreetingService {
  override def hello(name: String) = ServiceCall { _ =>
    val person = repo.personById(name)
    Future.successful(s"Hello $name with person: $person")
  }
}
