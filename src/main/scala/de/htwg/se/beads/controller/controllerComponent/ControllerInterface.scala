package de.htwg.se.beads.controller.controllerComponent
import de.htwg.se.beads.model.templateComponent.BeadInterface
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.Stitch

import scala.swing.Publisher


trait ControllerInterface extends Publisher{
  def createEmptyTemplate(length: Int, width: Int,stitch:Stitch.Value): Unit

  def tempToString: String

  def setColor(row: Int, col: Int, color: java.awt.Color): Unit

  def changeSize(length: Int, width: Int): Unit

  def changeRowColor(row:Int,color: java.awt.Color):Unit

  def changeColumnColor(col:Int,color: java.awt.Color):Unit

  def changeTemplateColor(color: java.awt.Color):Unit

  def undo():Unit
  def redo():Unit

  def bead(row:Int, col:Int):BeadInterface

  def isFilled(row:Int, col:Int):Boolean

  def tempLength:Int
  def tempWidth:Int
}


import scala.swing.event.Event

class BeadChanged extends Event
case class TemplateSizeChanged(length:Int,width:Int) extends Event
case class TemplateChanged(length:Int, width:Int, stitch:Stitch.Value) extends Event