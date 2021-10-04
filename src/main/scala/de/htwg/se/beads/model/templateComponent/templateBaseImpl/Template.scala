package de.htwg.se.beads.model.templateComponent.templateBaseImpl

import java.awt.Color.{DARK_GRAY, LIGHT_GRAY}

import com.google.inject.Inject
import de.htwg.se.beads.model.templateComponent.TemplateInterface

case class Template@Inject()(beads:Matrix) extends TemplateInterface{

  val startColor = LIGHT_GRAY
  def this(length: Int, width: Int, stitch: Stitch.Value) = this(new Matrix(length, width, Bead(Coord(0, 0), stitch, LIGHT_GRAY)))

  val stitch:Stitch.Value = bead(0, 0).beadStitch
  val size_rows: Int = beads.size._1
  val size_cols: Int = beads.size._2

  def bead(row: Int, col: Int): Bead = beads.bead(row, col)

  def setColor(row: Int, col: Int, color: java.awt.Color): Template = {
    val oldbead = bead(row, col)
    copy(beads.replaceBead(row, col, new Bead(oldbead.beadCoord, oldbead.beadStitch, color)))
  }

  def changeSize(l: Int, w: Int): Template = copy(new Matrix(l, w, Bead(Coord(0, 0), this.stitch, startColor)))

  def newTemplate(l: Int, w: Int, stitch: Stitch.Value): Template = copy(new Matrix(l, w,Bead(Coord(0, 0), stitch, startColor)))

  def row(row: Int): Vektor = Vektor(beads.matrix(row))

  def setRowColor(row: Int, color: java.awt.Color): Template = {
    copy(beads.replaceRowColor(row, color))
  }

  def col(col: Int): Vektor = Vektor(beads.matrix.map(row => row(col)))

  def setColumnColor(col: Int, color: java.awt.Color): Template = {
    copy(beads.replaceColumnColor(col, color))
  }

  def changeTemplateColor(color: java.awt.Color): Template = {
    copy(beads.replaceMatrixColor(color))
  }

  object String  {

    var strategy = if (stitch.equals(Stitch.Brick)) strategy1 else strategy2

    def strategy1:String = {
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

    def strategy2:String = {
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
//object Template {
//  import play.api.libs.json._
//  implicit val templateWrites = Json.writes[Template]
//  implicit val templateReads = Json.reads[Template]
//}
