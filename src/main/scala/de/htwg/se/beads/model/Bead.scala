package de.htwg.se.beads.model
import scala.io.AnsiColor.RESET

object Stitch extends Enumeration {
  val Brick, Square, Fringe = Value
}

case class Bead(beadCoord:Coord,
                beadStitch:Stitch.Value,
                beadColor:Color = Color(255,255,255)){


  def isFilled: Boolean = beadColor != Color(255,255,255)

  def changeColor(color:Color): Bead = copy(beadCoord,beadStitch,color)

  def changeStitch(newStitch: Stitch.Value): Bead = copy(beadCoord, newStitch,beadColor)

  override def equals(that: Any): Boolean = that match {
    case that:Bead => that.canEqual(this) && this.beadCoord.equals(that.beadCoord) && this.beadStitch.equals(that.beadStitch) && this.beadColor.equals(that.beadColor)
    case _ => false
  }

  def addBeadRight(): Bead = {Bead(Coord(beadCoord.x + 1, beadCoord.y), beadStitch, beadColor)}

  def addBeadUp(): Bead = {Bead(Coord(beadCoord.x, beadCoord.y + 1),beadStitch, beadColor)}

  def addBeadLeft(): Bead = {Bead(Coord(beadCoord.x - 1, beadCoord.y),beadStitch,beadColor)}

  override def toString: String = {
    s"|${rgbToAnsi.colors.get(beadColor).get}   ${RESET}|"
  }
}
