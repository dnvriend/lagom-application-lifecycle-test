package com.github.dnvriend.lifecycle

import com.github.dnvriend.lifecycle.api.GreetingService
import com.github.dnvriend.lifecycle.application.GreetingApplication
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server._

class GreetingLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext): LagomApplication =
    new GreetingApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new GreetingApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[GreetingService]
  )
}


