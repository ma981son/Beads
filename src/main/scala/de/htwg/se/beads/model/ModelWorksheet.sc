import scala.collection.immutable.Vector
import scala.io.AnsiColor.RESET
import de.htwg.se.beads.model.{Bead, Color, Coord, Matrix, Stitch, Template, Vektor, rgbToAnsi}

var red = Color(255.0,0.0,0.0)
var white = Color(255,255,255)

object Stitch extends Enumeration {
  val Brick, Square, Fringe = Value
}

case class Coord(x:Double,y:Double){
  override def toString:String = x+","+y
}

case class Bead(beadCoord:Coord,
                beadStitch:Stitch.Value,
                beadColor:Color = Color(255,255,255)){


  def isFilled: Boolean = beadColor != white

  def changeColor(color:Color): Bead = copy(beadCoord,beadStitch,color)

  def changeStitch(newStitch: Stitch.Value): Bead = copy(beadCoord, newStitch,beadColor)

  override def equals(that: Any): Boolean = that match {
    case that:Bead => that.canEqual(this) && this.beadCoord.equals(that.beadCoord) && this.beadStitch.equals(that.beadStitch) && this.beadColor.equals(that.beadColor)
    case _ => false
  }

  def addBeadRight: Bead = {
    if (!beadStitch.equals(Stitch.Fringe)) {
      val newBead = Bead(Coord(beadCoord.x + 1, beadCoord.y),beadStitch, beadColor)
      return newBead
    }
    None.get
  }

  def addBeadUp: Bead = {
    if (!beadStitch.equals(Stitch.Fringe) && !beadStitch.equals(Stitch.Brick)) {
      val newBead = Bead(Coord(beadCoord.x, beadCoord.y + 1),beadStitch, beadColor)
      return newBead
    }
    None.get
  }

  def addBeadLeft: Bead = {
    if (!beadStitch.equals(Stitch.Fringe)) {return Bead(Coord(beadCoord.x - 1, beadCoord.y),beadStitch,beadColor)}
    None.get

  }

  override def toString: String = {
    val standard = Color(255.0,255.0,255.0).toString().size
    val beadsize = beadColor.toString().size
    if(beadsize < standard){
      val space = (standard - beadsize) /2
      "| "+ "  " * space + beadColor+ "  " * space + " |"
    }
    s"| "+ beadColor +" |"
  }

}

var bead1 = new Bead(Coord(0,0), Stitch.Square, white)

bead1.beadCoord.x
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
bead1.changeColor(white).toString
bead2.changeColor(red).toString

bead1.equals(bead1)


case class House(beads:Vector[Bead]){
  def colorHouse(color: Color):House = copy(beads.map(bead => bead.changeColor(color)))
}

val t = Vector.fill(2)(bead1)
val house = new House(t)
house.colorHouse(white)

implicit class x[A](as: Seq[A]) {
  def updatedAt(is: collection.Traversable[Int], a: A) = {
    (as /: is) { case (xx, i) => xx updated (i, a) } } }


case class Matrix(matrix:Vector[Vector[Bead]]) {

  def this(row: Int = 0, col: Int = 0, startBead: Bead) =
    this(Vector.tabulate(row, col) { (x, y) => Bead(Coord(x, y), startBead.beadStitch, startBead.beadColor) })

  def replaceBead(row:Int, col:Int, cell:Bead):Matrix =
    copy(matrix.updated(row, matrix(row).updated(col, cell)))

  def replaceRowColor(row:Int, color: Color):Matrix =
    copy(matrix.updated(row,matrix(row).map(bead=>bead.changeColor(color))))

  def replaceColumnColor(col:Int, color: Color):Matrix =
    copy(matrix.transpose.updated(col,matrix(col).map(bead=>bead.changeColor(color))).transpose)

  def replaceMatrixColor(color: Color):Matrix=
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
  def this(length: Int, width: Int, stitch: Stitch.Value) = this(new Matrix(length, width, Bead(Coord(0, 0), stitch, Color(255, 255, 255))))

  val stitch = bead(0,0).beadStitch

  val size_rows: Int = beads.size._1
  val size_cols: Int = beads.size._2

  def bead(row: Int, col: Int): Bead = beads.bead(row, col)

  def setColor(row: Int, col: Int, color: Color): Grid = {
    val oldbead = bead(row, col)
    copy(beads.replaceBead(row, col, Bead(oldbead.beadCoord, oldbead.beadStitch, color)))
  }

  def row(row: Int): House = House(beads.matrix(row))
  def setRowColor(row:Int, color: Color):Grid = {
    copy(beads.replaceRowColor(row,color))
  }

  def col(col: Int): House = House(beads.matrix.map(row => row(col)))
  def setColumnColor(col:Int, color: Color):Grid = {
    copy(beads.replaceColumnColor(col,color))
  }

  def changeTemplateColor(color: Color):Grid={
    copy(beads.replaceMatrixColor(color))
  }

  def changeSize(l:Int,w:Int): Grid = copy(new Matrix(l,w,Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))))

  override def toString: String = {

    if(stitch.equals(Stitch.Brick)){
      val regex = "x".r
      val line1 = " x " * size_cols + "\n"
      val line2 = "x" * size_cols + "\n"
      var lineseparator = ("-" * beads.bead(0,0).toString.size) * size_cols + "\n"
      var box = "\n" + ((line1 + line2)* (size_rows/2))
      if(size_rows%2!=0) {
        box = box + line1
      }
      return box
      }


    val regex = "x".r
    val line = "x" * size_cols + "\n"
    var lineseparator = ("-" * beads.bead(0,0).toString.size) * size_cols + "\n"
    var box = "\n" + (line * size_rows)
    for (row <- 0 until size_rows) {
      for (col <- 0 until size_cols) {
        lineseparator = ("-" * bead(size_rows-1,size_cols-1).toString.size) * size_cols + "\n"
        box = regex.replaceFirstIn(box, bead(row, col).toString)
      }
    }
    box
  }
}

val gridrot = new Grid(5,2,Stitch.Brick)


val r = new Matrix(2,2,bead1)
r.matrix.map(_(0)).map(bead => bead.changeColor(white))

val grid1 = new Grid(3,2,Stitch.Square)
grid1.bead(1,0).beadCoord
grid1.bead(0,0).isFilled
val blue = Color(0,0,255)
val grid2 = grid1.setColor(0,0,red).setColor(1,0,red).setColor(2,0,red)

grid2.col(0)

grid2.col(1)

val coloredcol = grid2.col(1)

grid2.setColumnColor(0,blue)




