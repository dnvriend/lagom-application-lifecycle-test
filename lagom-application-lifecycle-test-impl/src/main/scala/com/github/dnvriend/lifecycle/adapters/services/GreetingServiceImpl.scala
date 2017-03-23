package com.github.dnvriend.lifecycle.adapters.services

import com.github.dnvriend.lifecycle.adapters.components.H2ConnectionPool
import com.github.dnvriend.lifecycle.api.GreetingService
import com.lightbend.lagom.scaladsl.api.ServiceCall

import scala.concurrent.{ExecutionContext, Future}

class GreetingServiceImpl(pool: H2ConnectionPool)(implicit ec: ExecutionContext) extends GreetingService {
  override def hello(name: String) = ServiceCall { _ =>
    pool.withConnection { conn =>
      println(s"'$name' is using connection: '$conn'")
    }
    Future.successful(s"Hello $name")
  }
}
