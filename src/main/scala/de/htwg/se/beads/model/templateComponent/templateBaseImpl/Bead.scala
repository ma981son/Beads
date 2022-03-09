package de.htwg.se.beads.model.templateComponent.templateBaseImpl

import de.htwg.se.beads.model.templateComponent.BeadInterface
import play.api.libs.json.{Format, Json}

import scala.io.AnsiColor.RESET
import scala.language.postfixOps

object Stitch extends Enumeration {
  val Brick, Square, Fringe, Brazil, Huichol_3, Huichol_5  = Value //No var in model layer. Used val
  implicit val format: Format[Stitch.Value] = Json.formatEnum(Stitch)
}

case class Bead(beadCoord:Coord,
                beadStitch:Stitch.Value,
                beadColor:java.awt.Color) extends BeadInterface{ // No getter or Setter -> Constructor part of declaration

  def isFilled: Boolean = beadColor != java.awt.Color.LIGHT_GRAY

  def changeColor(color:java.awt.Color): Bead = copy(beadCoord,beadStitch,color) //Functional copying -> No state

  def changeStitch(newStitch: Stitch.Value): Bead = copy(beadCoord, newStitch,beadColor) //Functional copying -> No state

  override def equals(that: Any): Boolean = that match {
    case that:Bead => that.canEqual(this) && this.beadCoord.equals(that.beadCoord) && this.beadStitch.equals(that.beadStitch) && this.beadColor.equals(that.beadColor)
    case _ => false
  }

  def addBeadRight(): Bead = {Bead(Coord(beadCoord.x + 1, beadCoord.y), beadStitch, beadColor)}

  def addBeadUp(): Bead = {Bead(Coord(beadCoord.x, beadCoord.y + 1),beadStitch, beadColor)}

  def addBeadLeft(): Bead = {Bead(Coord(beadCoord.x - 1, beadCoord.y),beadStitch,beadColor)}

  override def toString: String = {
    if(awtColorToAnsi.colors.contains(beadColor)){
      return s"|${awtColorToAnsi.colors(beadColor)}    $RESET|"
    }
    s"|$beadColor    $RESET|"
  }
}