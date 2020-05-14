
case class Color(r:Double, g:Double, b:Double)

var red = Color(255.0,0.0,0.0)
var white = Color(255,255,255)

object Stitch extends Enumeration {
  val Brick, Square, Fringe = Value
}

case class Coord(x:Double,y:Double)

case class Bead(beadCoord:model.Coord, beadStitch:Stitch.Value, beadColor:model.Color = white) {

  def isFilled: Boolean = beadColor != white

  def changeColor(color: model.Color): Bead = copy(beadCoord,beadStitch,color)

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

case class House(Beads:Vector[Bead])

val house = House(Vector(bead1,bead2))

house.Beads(0)
house.Beads(1)

case class Matrix[T] (rows:Vector[Vector[T]]){
  def this(size:Int, filling:T) = this(Vector.tabulate(size, size){(row,col) => filling})
  val size:Int = rows.size
  def cell(row:Int, col:Int):T = rows (row)(col)
  def replaceCell(row:Int,col:Int,Bead:T):Matrix[T] = copy(rows.updated(row,rows(row).updated(col,Bead)))
  def fill(filling:T):Matrix[T] = copy(Vector.tabulate(size,size){(row,col) => filling})
}

val matrix = Matrix(Vector(Vector(bead1,bead2)))

matrix.size