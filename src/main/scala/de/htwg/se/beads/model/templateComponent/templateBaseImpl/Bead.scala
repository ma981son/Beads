package de.htwg.se.beads.model.templateComponent.templateBaseImpl

import java.awt
import java.awt.Color._

import com.fasterxml.jackson.core.{JsonGenerator, JsonParser}
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind._
import de.htwg.se.beads.model.templateComponent.BeadInterface
import play.api.libs.functional.syntax._
import scala.language.postfixOps

import scala.io.AnsiColor.RESET

object Stitch extends Enumeration {
  val Brick, Square, Fringe = Value
}

case class Bead(beadCoord:Coord,
                beadStitch:Stitch.Value,
                beadColor:java.awt.Color) extends BeadInterface{


  def isFilled: Boolean = beadColor != WHITE

  def changeColor(color:java.awt.Color): Bead = copy(beadCoord,beadStitch,color)

  def changeStitch(newStitch: Stitch.Value): Bead = copy(beadCoord, newStitch,beadColor)

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