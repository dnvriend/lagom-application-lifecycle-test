package com.github.dnvriend.lifecycle.adapters.components

import java.util.UUID

import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

trait DatabaseComponents {
  def applicationLifecycle: ApplicationLifecycle
  lazy val h2ConnectionPool = new H2ConnectionPool
  applicationLifecycle.addStopHook(h2ConnectionPool.stop)
}

case class Connection(name: String)
class H2ConnectionPool {
  println("Starting H2ConnectionPool")
  def randomId(): String = UUID.randomUUID.toString
  def getConnection(): Connection = {
    val conn = Connection(randomId())
    println(s"Getting connection: '$conn'")
    conn
  }
  def returnConnection(conn: Connection): Unit = {
    println(s"Returning connection: '$conn'")
  }
  def withConnection[A](block: Connection => A): A = {
    val conn = getConnection()
    try block(conn) finally returnConnection(conn)
  }
  val stop = () => Future.successful(println("Stopping H2ConnectionPool"))
}