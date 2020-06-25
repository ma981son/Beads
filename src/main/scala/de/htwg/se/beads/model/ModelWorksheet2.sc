import de.htwg.se.beads.model.{Color, Coord, Stitch}
import jdk.javadoc.internal.doclets.formats.html.resources.standard

import scala.collection.immutable.Vector

var red = Color(255.0,0.0,0.0)
var white = Color(255,255,255)

object BeadState extends Enumeration{
  type BeadState = Value
  val NULLBEAD,IDLE = Value
}

case class Bead(beadCoord:Coord,
                beadStitch:Stitch.Value,
                beadColor:Color = Color(255,255,255),
                beadState:BeadState.Value){

  def this(coord:Coord, stitch:Stitch.Value, color:Color) = this(coord,stitch,color,BeadState.IDLE)


  def isFilled: Boolean = beadState != BeadState.NULLBEAD

  def changeColor(color:Color): Bead = copy(beadCoord,beadStitch,color)

  def changeStitch(newStitch: Stitch.Value): Bead = copy(beadCoord, newStitch,beadColor)

  override def equals(that: Any): Boolean = that match {
    case that:Bead => that.canEqual(this) && this.beadCoord.equals(that.beadCoord) && this.beadStitch.equals(that.beadStitch) && this.beadColor.equals(that.beadColor)
    case _ => false
  }

  object StringStrategy {

    var strategy = if (beadState.equals(BeadState.NULLBEAD)) strategy1 else strategy2

    def strategy1:String = s"|                          |"

    def strategy2:String = s"| "+ beadColor +" |"
  }

  override def toString: String = {
    StringStrategy.strategy
  }

}




case class Matrix(matrix:Vector[Vector[Bead]]) {

  def this(row: Int = 0, col: Int = 0, startBead: Bead) =
    this(Vector.tabulate(row, col) { (x, y) => Bead(Coord(x, y), startBead.beadStitch, startBead.beadColor,BeadState.NULLBEAD) })


  def replaceBead(row:Int, col:Int, bead:Bead):Matrix =
    copy(matrix.updated(row, matrix(row).updated(col, bead)))

  def replaceRowColor(row:Int, color: Color):Matrix = {
    val vector = matrix(row).filter( x  => !x.beadState.equals(BeadState.NULLBEAD) )
    if (vector.isEmpty){
      return copy(matrix)
    }
      copy(matrix.updated(row,matrix(row).filter( x  => !x.beadState.equals(BeadState.NULLBEAD) ).map(bead=>bead.changeColor(color))))
  }

  def replaceColumnColor(col:Int, color: Color):Matrix = {
    val vector = matrix(col).collect { case x: Bead if !x.beadState.equals(BeadState.NULLBEAD) => x }
    if (vector.isEmpty){
      return copy(matrix)
    }
    copy(matrix.transpose.updated(col, vector.map(bead => bead.changeColor(color))).transpose)
  }

  def replaceMatrixColor(color: Color):Matrix= {
    val vector = matrix.map(vector => vector.collect { case x: Bead if !x.beadState.equals(BeadState.NULLBEAD) => x })
    if (vector.isEmpty){
      return copy(matrix)
    }
    copy(vector.map(vector=>vector.map(bead=>bead.changeColor(color))))
  }

  val size: (Int,Int) = (matrix.size,matrix.head.size)

  def bead(row:Int, col:Int):Bead = {
    matrix(row)(col)
  }

}


case class House(beads:Vector[Bead]){
  def colorHouse(color: Color):House = copy(beads.map(bead => bead.changeColor(color)))
}

case class Grid(beads:Matrix) {
  def this(length: Int, width: Int, stitch: Stitch.Value) = this(new Matrix(length, width, new Bead(Coord(0, 0), stitch, Color(255, 255, 255))))

  val stitch = bead(0, 0).beadStitch

  val size_rows: Int = beads.size._1
  val size_cols: Int = beads.size._2

  def bead(row: Int, col: Int): Bead = beads.bead(row, col)

  def setColor(row: Int, col: Int, color: Color): Grid = {
    val oldbead = bead(row, col)
    copy(beads.replaceBead(row, col, new Bead(oldbead.beadCoord, oldbead.beadStitch, color)))
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

  def changeSize(l:Int,w:Int): Grid = copy(new Matrix(l,w, new Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))))

    object String {
    var strategy = if (stitch.equals(Stitch.Brick)) strategy1 else strategy2

    def strategy1: String = {
      val regex = "x".r
      val line = "x" * size_cols + "\n"
      var lineseparator = ("-" * 6) * size_cols + "\n"
      val line1 = " " * 3 + line
      lineseparator = "---" + lineseparator
      var box = "\n" + (lineseparator + (line1 + lineseparator + line + lineseparator) * (size_rows / 2))
      if (size_rows % 2 != 0) {
        box = box + line1 + lineseparator
      }
      for (row <- 0 until size_rows) {
        for (col <- 0 until size_cols) {
          box = regex.replaceFirstIn(box, bead(row, col).toString)
        }
      }
      return box
    }

    def strategy2: String = {
      val regex = "x".r
      val line = "x" * size_cols + "\n"
      var lineseparator = ("-" * 24) * size_cols + "\n"
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


val bead1 = new Bead(Coord(0,0),Stitch.Square,white)
bead1.toString
val beadN = Bead(Coord(0,0),Stitch.Square,red,BeadState.NULLBEAD)
val matrix = new Matrix(2,2,bead1)

val grid1 = new Grid(2,2,Stitch.Square)
val grid2 = Grid(matrix).setColor(0,0,white).setRowColor(0,red)