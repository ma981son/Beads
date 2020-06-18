package de.htwg.se.beads.model

case class Template(beads:Matrix) {
  def this(length: Int, width: Int, stitch: Stitch.Value) = this(new Matrix(length, width, Bead(Coord(0, 0), stitch, Color(255, 255, 255))))

  val stitch = bead(0,0).beadStitch
  val size_rows: Int = beads.size._1
  val size_cols: Int = beads.size._2

  def bead(row: Int, col: Int): Bead = beads.bead(row, col)

  def setColor(row: Int, col: Int, color:Color): Template = {
    val oldbead = bead(row, col)
    copy(beads.replaceBead(row, col, Bead(oldbead.beadCoord, oldbead.beadStitch, color)))
  }

  def changeSize(l:Int,w:Int): Template = copy(new Matrix(l,w,Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))))
  def changeTemplate(l:Int,w:Int,stitch: Stitch.Value): Template = copy(new Matrix(l,w,Bead(Coord(0, 0), stitch, Color(255, 255, 255))))

  def row(row: Int): Vektor = Vektor(beads.matrix(row))
  def setRowColor(row:Int, color: Color):Template = {
    copy(beads.replaceRowColor(row,color))
  }

  def col(col: Int): Vektor = Vektor(beads.matrix.map(row => row(col)))
  def setColumnColor(col:Int, color: Color):Template = {
    copy(beads.replaceColumnColor(col,color))
  }

  def changeTemplateColor(color: Color):Template={
    copy(beads.replaceMatrixColor(color))
  }

  override def toString: String = {
    if(stitch.equals(Stitch.Brick)){
      val regex = "x".r
      val line2 = "x" * size_cols + "\n"
      val line1 = " " * 3 + line2
      var lineseparator = "---" + ("-" * 6) * size_cols + "\n"
      var box = "\n" + (lineseparator + (line1 + lineseparator + line2 + lineseparator)* (size_rows/2))
      if(size_rows%2!=0) {
        box = box + line1 + lineseparator
      }
      for (row <- 0 until size_rows) {
        for (col <- 0 until size_cols) {
          box = regex.replaceFirstIn(box, bead(row, col).toString)
        }
      }
      return box
    }
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
