organization in ThisBuild := "com.github.dnvriend"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

lagomKafkaEnabled := false
lagomKafkaCleanOnStart := true
lagomCassandraEnabled := false
lagomCassandraCleanOnStart := true

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

lazy val `lagom-application-lifecycle-test` = (project in file("."))
  .aggregate(`lagom-application-lifecycle-test-api`, `lagom-application-lifecycle-test-impl`)

lazy val `lagom-application-lifecycle-test-api` = (project in file("lagom-application-lifecycle-test-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `lagom-application-lifecycle-test-impl` = (project in file("lagom-application-lifecycle-test-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`lagom-application-lifecycle-test-api`)

