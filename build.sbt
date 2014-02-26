organization := "arimitsu.sf"

name := "undertow-with-spring"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
  "io.undertow" % "undertow-core" % "1.0.0.Final",
  "org.springframework" % "spring-webmvc" % "4.0.1.RELEASE",
  "org.springframework" % "spring-context" % "4.0.1.RELEASE",
  "org.scalaz" %% "scalaz-core" % "7.0.5" withSources()
)
