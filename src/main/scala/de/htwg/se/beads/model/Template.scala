package de.htwg.se.beads.model

case class Template(beads:Matrix) {
  def this(length: Int, width: Int) = this(new Matrix(length, width, Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))))

  val size_rows: Int = beads.size._1
  val size_cols: Int = beads.size._2

  def bead(row: Int, col: Int): Bead = beads.bead(row, col)

  def setColor(row: Int, col: Int, color:Color): Template = {
    val oldbead = bead(row, col)
    copy(beads.replaceBead(row, col, Bead(oldbead.beadCoord, oldbead.beadStitch, color)))
  }

  def changeSize(l:Int,w:Int): Template = copy(new Matrix(l,w,Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))))

  def row(row: Int): Vektor = Vektor(beads.matrix(row))

  def col(col: Int): Vektor = Vektor(beads.matrix.map(row => row(col)))

  override def toString: String = {
    val regex = "x".r
    val line = "x" * size_cols + "\n"
    var lineseparator = ("-" * 5) * size_cols + "\n"
    var box = "\n" + (lineseparator + (line + lineseparator) * size_rows)

    for (row <- 0 until size_rows) {
      for (col <- 0 until size_cols) {
        box = regex.replaceFirstIn(box, bead(row, col).toString)
      }
    }
    box
  }
}
