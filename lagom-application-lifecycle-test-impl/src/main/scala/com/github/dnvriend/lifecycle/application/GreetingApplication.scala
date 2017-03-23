package com.github.dnvriend.lifecycle.application

import com.github.dnvriend.lifecycle.adapters.components.DatabaseComponents
import com.github.dnvriend.lifecycle.adapters.repository.PersonRepository
import com.github.dnvriend.lifecycle.adapters.services.GreetingServiceImpl
import com.github.dnvriend.lifecycle.api.GreetingService
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomServer}
import com.softwaremill.macwire.wire
import play.api.libs.ws.ahc.AhcWSComponents

abstract class GreetingApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents
    with DatabaseComponents {

    lazy val repo: PersonRepository = wire[PersonRepository]

    override lazy val lagomServer: LagomServer = LagomServer.forServices(
      bindService[GreetingService].to(wire[GreetingServiceImpl])
    )
  }
