package de.htwg.se.model

object Stitch extends Enumeration {
  val Brick, Square, Fringe = Value
}
case class Bead(beadCoord:Coord, beadStitch:Stitch.Value, beadColor:Color = Color(255,255,255)) {

  def isFilled: Boolean = beadColor != Color(255,255,255)

  def changeColor(color: Color): Bead = copy(beadCoord,beadStitch,color)

  def changeStitch(newStitch: Stitch.Value): Bead = copy(beadCoord, newStitch,beadColor)

  def addBeadRight: Bead = {
    if (!beadStitch.equals(Stitch.Fringe)) {
      val newBead = Bead(Coord(beadCoord.x + 1, beadCoord.y),beadStitch, beadColor)
      return newBead
    }
    None.get
  }

  def addBeadLeft: Bead = {
    if (!beadStitch.equals(Stitch.Fringe)) {return Bead(Coord(beadCoord.x - 1, beadCoord.y),beadStitch,beadColor)}
    None.get
  }

}
