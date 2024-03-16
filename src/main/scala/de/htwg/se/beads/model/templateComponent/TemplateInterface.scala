package de.htwg.se.beads.model.templateComponent

import de.htwg.se.beads.model.templateComponent.templateBaseImpl.{Coord, Stitch, Vektor}
import play.api.libs.json.JsValue

trait TemplateInterface {

  val stitch :Stitch.Value
  val size_rows: Int
  val size_cols: Int

  def bead(row: Int, col: Int): BeadInterface

  def setColor(row: Int, col: Int, color: java.awt.Color): TemplateInterface

  def changeSize(l: Int, w: Int): TemplateInterface

  def newTemplate(l: Int, w: Int, stitch: Stitch.Value): TemplateInterface

  def row(row: Int): Vektor

  def setRowColor(row: Int, color: java.awt.Color): TemplateInterface

  def col(col: Int): Vektor

  def setColumnColor(col: Int, color: java.awt.Color): TemplateInterface

  def changeTemplateColor(color: java.awt.Color): TemplateInterface

  def toJson:JsValue

}

trait BeadInterface {

  val beadColor:java.awt.Color
  val beadStitch:Stitch.Value
  val beadCoord:Coord

  def isFilled: Boolean

  def changeColor(color:java.awt.Color): BeadInterface

  def changeStitch(newStitch: Stitch.Value): BeadInterface

  def addBeadRight(): BeadInterface

  def addBeadUp(): BeadInterface

  def addBeadLeft(): BeadInterface
}
