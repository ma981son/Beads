name := "Beads"
organization  := "de.htwg.se"
version := "0.0.1"
scalaVersion := "2.13.2"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.2" % "test"

// https://mvnrepository.com/artifact/org.scoverage/scalac-scoverage-plugin
libraryDependencies += "org.scoverage" %% "scalac-scoverage-plugin" % "1.4.1" % "provided"

//*******************************************************************************//
//Libraries that we will use in later lectures compatible with this scala version
// uncomment to use!!

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.10+"

libraryDependencies += "com.google.inject" % "guice" % "5.0.1"

libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.10"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.2"

libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.11.4"