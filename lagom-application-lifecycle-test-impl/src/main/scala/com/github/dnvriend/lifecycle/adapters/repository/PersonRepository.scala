package com.github.dnvriend.lifecycle.adapters.repository

import com.github.dnvriend.lifecycle.adapters.components.H2ConnectionPool
import com.github.dnvriend.lifecycle.adapters.repository.PersonRepository.Person

object PersonRepository {
  case class Person(id: String, name: String, age: Int)
}

class PersonRepository(pool: H2ConnectionPool) {
  def personById(id: String): Option[Person] = {
    pool.withConnection { conn =>
      println(s" PersonRepository using connection: '$conn'")
      Option(Person(conn.name, "foo", 42))
    }
  }
}
