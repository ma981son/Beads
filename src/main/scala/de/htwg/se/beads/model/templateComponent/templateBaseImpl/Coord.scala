package de.htwg.se.beads.model.templateComponent.templateBaseImpl

import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, Reads, Writes}

case class Coord(x:Double, y:Double){
  override def toString:String = x+","+y
}
