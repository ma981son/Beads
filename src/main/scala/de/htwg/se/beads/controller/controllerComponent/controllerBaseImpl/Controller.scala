package de.htwg.se.beads.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject}
import de.htwg.se.beads.BeadModule
import de.htwg.se.beads.controller.controllerComponent.{BeadChanged, ControllerInterface, TemplateChanged, TemplateSizeChanged}
import de.htwg.se.beads.model.fileIoComponent.fileIoJsonImpl.FileIO
import de.htwg.se.beads.model.templateComponent.TemplateInterface
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.Stitch
import de.htwg.se.beads.util.UndoManager
import play.api.libs.json.JsValue

import scala.swing.Publisher


class Controller@Inject()(var temp: TemplateInterface) extends ControllerInterface with Publisher{

  private val undoManager: UndoManager = new UndoManager
  val injector = Guice.createInjector(new BeadModule)

  def createEmptyTemplate(length: Int, width: Int,stitch:Stitch.Value): Unit = {
    undoManager.doStep(new createTemplateCommand(length,width,stitch,this))
    publish(new TemplateChanged(length,width,stitch))
  }

  def tempToString: String = temp.toString

  def setColor(row: Int, col: Int, color: java.awt.Color): Unit = {
    undoManager.doStep(new SetColorCommand(row,col,color,this))
    publish(new BeadChanged)
  }

  def changeSize(length: Int, width: Int): Unit = {
    temp = temp.changeSize(length, width)
    publish(new TemplateSizeChanged(length,width))
  }

  def changeStitch(stitch: Stitch.Value)={
    undoManager.doStep(new createTemplateCommand(tempLength,tempWidth,stitch,this))
    publish(TemplateChanged(tempLength, tempWidth, stitch))
  }

  def changeRowColor(row:Int,color: java.awt.Color):Unit={
    undoManager.doStep(new changeRowColorCommand(row,color,this))
    publish(new BeadChanged)
  }

  def changeColumnColor(col:Int,color: java.awt.Color):Unit={
    undoManager.doStep(new changeColumnColorCommand(col,color,this))
    publish(new BeadChanged)
  }

  def changeTemplateColor(color: java.awt.Color):Unit={
    undoManager.doStep(new changeTemplateColorCommand(color,this))
    publish(new BeadChanged)
  }

  def undo():Unit={
    undoManager.undoStep()
    publish(new BeadChanged)
  }

  def redo():Unit={
    undoManager.redoStep()
    publish(new BeadChanged)
  }

  def save(): Unit = {
    val fileIO = new FileIO
    fileIO.save(temp)
  }

  def load():Unit={
    val fileIO = new FileIO
    temp = fileIO.load
    publish(TemplateChanged(tempLength,tempWidth,stitch))
  }

  def toJson: JsValue = temp.toJson

  def bead(row:Int, col:Int) = temp.bead(row,col)
  def stitch = temp.stitch
  def isFilled(row:Int, col:Int):Boolean = temp.bead(row, col).isFilled
  def tempLength = temp.size_rows
  def tempWidth = temp.size_cols
}
