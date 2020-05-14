
case class Color(r:Double, g:Double, b:Double)

var red = Color(255.0,0.0,0.0)
var white = Color(255,255,255)

object Stitch extends Enumeration {
  val Brick, Square, Fringe = Value
}

case class Coord(x:Double,y:Double)



case class Bead(x:Double, y:Double, beadStitch:Stitch.Value, beadColor:Color = white) {

  def isFilled: Boolean = beadColor != white

  def changeColor(color: Color): Bead = copy(x,y,beadStitch,color)

  def changeStitch(newStitch: Stitch.Value): Bead = copy(x, y, newStitch,beadColor)

  def addBeadRight: Bead = {
    if (!beadStitch.equals(Stitch.Fringe)) {
      val newBead =Bead(x + 1, y,beadStitch, beadColor)

      return newBead
    }
    None.get
  }

  def addBeadLeft: Bead = {
    if (!beadStitch.equals(Stitch.Fringe)) {return Bead(x-1, y, beadStitch,beadColor)}
    None.get
  }

}


var bead1 = new Bead(0, 0, Stitch.Square, white)
bead1.isFilled
bead1.x
bead1.y
bead1 = bead1.changeColor(red)
bead1.isFilled
bead1.beadColor
bead1.beadStitch
bead1 = bead1.changeStitch(Stitch.Brick)
bead1.beadStitch
var bead2 = bead1.addBeadRight
bead2 = bead2.changeColor(red)
var bead3 = bead1.addBeadLeft
bead1.addBeadRight.addBeadLeft

