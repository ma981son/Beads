import scala.collection.immutable.Vector
import scala.io.AnsiColor.RESET
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.{Color, Stitch, awtColorToAnsi}
import java.awt.Color._
var red = Color(255.0,0.0,0.0)
var white = Color(255,255,255)

case class Coord(x:Double,y:Double){
  override def toString:String = x+","+y
}

case class Bead(beadCoord:Coord,
                beadStitch:Stitch.Value,
                beadColor:java.awt.Color = java.awt.Color.WHITE) {


  def isFilled: Boolean = beadColor != white

  def changeColor(color: java.awt.Color): Bead = copy(beadCoord, beadStitch, color)

  def changeStitch(newStitch: Stitch.Value): Bead = copy(beadCoord, newStitch, beadColor)

  override def equals(that: Any): Boolean = that match {
    case that: Bead => that.canEqual(this) && this.beadCoord.equals(that.beadCoord) && this.beadStitch.equals(that.beadStitch) && this.beadColor.equals(that.beadColor)
    case _ => false
  }

  def addBeadRight(): Bead = {
    if (!beadStitch.equals(Stitch.Fringe)) {
      val newBead = Bead(Coord(beadCoord.x + 1, beadCoord.y), beadStitch, beadColor)
      return newBead
    }
    None.get
  }

  def addBeadUp(): Bead = {
    if (!beadStitch.equals(Stitch.Fringe) && !beadStitch.equals(Stitch.Brick)) {
      val newBead = Bead(Coord(beadCoord.x, beadCoord.y + 1), beadStitch, beadColor)
      return newBead
    }
    None.get
  }

  def addBeadLeft(): Bead = {
    if (!beadStitch.equals(Stitch.Fringe)) {
      return Bead(Coord(beadCoord.x - 1, beadCoord.y), beadStitch, beadColor)
    }
    None.get

  }

  override def toString: String = {
    if (awtColorToAnsi.colors.contains(beadColor)) {
      return s"|${awtColorToAnsi.colors(beadColor)}    $RESET|"
    }
    s"|$beadColor    $RESET|"
  }

}

var bead1 = Bead(Coord(0, 0), Stitch.Square, WHITE)

bead1.beadCoord.x
bead1 = bead1.changeColor(java.awt.Color.RED)
bead1.isFilled
bead1.beadColor
bead1.beadStitch
bead1 = bead1.changeStitch(Stitch.Brick)
bead1.beadStitch
var bead2 = bead1.addBeadRight()
bead2 = bead2.changeColor(RED)
var bead3 = bead1.addBeadLeft()
bead1.addBeadRight().addBeadLeft()
bead1.changeColor(WHITE).toString
bead2.changeColor(RED).toString

bead1.equals(bead1)


case class House(beads:Vector[Bead]){
  def colorHouse(color: java.awt.Color):House = copy(beads.map(bead => bead.changeColor(color)))
}

val t = Vector.fill(2)(bead1)
val house = House(t)
house.colorHouse(WHITE)

implicit class x[A](as: Seq[A]) {
  def updatedAt(is: collection.Traversable[Int], a: A) = {
    (as /: is) { case (xx, i) => xx updated (i, a) } } }


case class Matrix(matrix:Vector[Vector[Bead]]) {

  def this(row: Int = 0, col: Int = 0, startBead: Bead) =
    this(Vector.tabulate(row, col) { (x, y) => Bead(Coord(x, y), startBead.beadStitch, startBead.beadColor) })

  def replaceBead(row:Int, col:Int, cell:Bead):Matrix =
    copy(matrix.updated(row, matrix(row).updated(col, cell)))

  def replaceRowColor(row:Int, color: java.awt.Color):Matrix =
    copy(matrix.updated(row,matrix(row).map(bead=>bead.changeColor(color))))

  def replaceColumnColor(col:Int, color: java.awt.Color):Matrix =
    copy(matrix.transpose.updated(col,matrix(col).map(bead=>bead.changeColor(color))).transpose)

  def replaceMatrixColor(color: java.awt.Color):Matrix=
    copy(matrix.map(vector=>vector.map(bead=>bead.changeColor(color))))

  val size: (Int,Int) = (matrix.size,matrix.head.size)
  def bead(row:Int, col:Int):Bead = matrix (row)(col)

  def fill (filling:Bead):Matrix = copy(
    Vector.tabulate(this.size._1, this.size._2){
      (row,col) => filling
    }
  )
}


case class Grid(beads:Matrix) {
  def this(length: Int, width: Int, stitch: Stitch.Value) = this(new Matrix(length, width, Bead(Coord(0, 0), stitch, WHITE)))

  val stitch = bead(0, 0).beadStitch

  val size_rows: Int = beads.size._1
  val size_cols: Int = beads.size._2

  def bead(row: Int, col: Int): Bead = beads.bead(row, col)

  def setColor(row: Int, col: Int, color: java.awt.Color): Grid = {
    val oldbead = bead(row, col)
    copy(beads.replaceBead(row, col, Bead(oldbead.beadCoord, oldbead.beadStitch, color)))
  }

  def row(row: Int): House = House(beads.matrix(row))

  def setRowColor(row: Int, color: java.awt.Color): Grid = {
    copy(beads.replaceRowColor(row, color))
  }

  def col(col: Int): House = House(beads.matrix.map(row => row(col)))

  def setColumnColor(col: Int, color: java.awt.Color): Grid = {
    copy(beads.replaceColumnColor(col, color))
  }

  def changeTemplateColor(color: java.awt.Color): Grid = {
    copy(beads.replaceMatrixColor(color))
  }

  def changeSize(l: Int, w: Int): Grid = copy(new Matrix(l, w, Bead(Coord(0, 0), Stitch.Square, WHITE)))

  object String {

    var strategy = if (stitch.equals(Stitch.Brick)) strategy1 else strategy2

    def strategy1: String = {
      val regex = "x".r
      val line = "x" * size_cols + "\n"
      var lineseparator = ("-" * 6) * size_cols + "\n"
      lineseparator = "---" + lineseparator
      val line1 = " " * 3 + line
      var box = "\n" + (lineseparator + (line1 + lineseparator + line + lineseparator) * (size_rows / 2))
      if (size_rows % 2 != 0) {
        box = box + line1 + lineseparator
      }
      for (row <- 0 until size_rows) {
        for (col <- 0 until size_cols) {
          box = regex.replaceFirstIn(box, bead(row, col).toString)
        }
      }
      box
    }

    def strategy2: String = {
      val regex = "x".r
      val line = "x" * size_cols + "\n"
      var lineseparator = ("-" * 6) * size_cols + "\n"
      var box = "\n" + (lineseparator + (line + lineseparator) * size_rows)
      for (row <- 0 until size_rows) {
        for (col <- 0 until size_cols) {
          box = regex.replaceFirstIn(box, bead(row, col).toString)
        }
      }
      box
    }
  }

  override def toString: String = {
    String.strategy
  }
}

val gridrot = new Grid(5,2,Stitch.Brick)


val r = new Matrix(2,2,bead1)
r.matrix.map(_(0)).map(bead => bead.changeColor(WHITE))

val grid1 = new Grid(3,2,Stitch.Square)
grid1.bead(1,0).beadCoord
grid1.bead(0,0).isFilled
val blue = Color(0,0,255)
val grid2 = grid1.setColor(0,0,RED).setColor(1,0,RED).setColor(2,0,RED)

grid2.col(0)

grid2.col(1)

val coloredcol = grid2.col(1)

val grid3 = new Grid(2,2,Stitch.Square)




