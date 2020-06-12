
name := "htwg-scala-seed"
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

//libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.1"

//libraryDependencies += "com.google.inject" % "guice" % "4.1.0"

//libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"

//libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6"

//libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6"
