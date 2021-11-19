package de.htwg.se.beads.model.templateComponent.templateBaseImpl

import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, Reads, Writes}

import scala.collection.immutable.Map
import scala.io.AnsiColor._

object stringToAnsi{
  val colors: Map[String, String] = Map("black" -> BLACK_B, "red" -> RED_B, "green" -> GREEN_B,
    "yellow" -> YELLOW_B, "blue" -> BLUE_B, "magenta" -> MAGENTA_B, "cyan" -> CYAN_B,
    "white" -> WHITE_B)
}

object rgbToAnsi{
  val colors: Map[Color, String] = Map(Color(0,0,0) -> BLACK_B, Color(255,0,0) -> RED_B, Color(0,255,0) -> GREEN_B,
    Color(255,255,0) -> YELLOW_B, Color(0,0,255) -> BLUE_B, Color(255,0,255) -> MAGENTA_B, Color(0,255,255) -> CYAN_B,
    Color(255,255,255) -> WHITE_B)
}

case class Color(r:Double, g:Double, b:Double) {
  override def toString: String = {
    "Color(" + r + "," + g + "," + b + ")"
  }
}
