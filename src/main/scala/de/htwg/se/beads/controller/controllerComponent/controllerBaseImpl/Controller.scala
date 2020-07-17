package de.htwg.se.beads.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.beads.BeadModule
import de.htwg.se.beads.controller.controllerComponent.{BeadChanged, ControllerInterface, TemplateChanged}
import de.htwg.se.beads.model.fileIoComponent.FileIoInterface
import de.htwg.se.beads.model.templateComponent.{BeadInterface, TemplateInterface}
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.Stitch
import de.htwg.se.beads.util.UndoManager

import scala.swing.Publisher


class Controller@Inject()(var temp: TemplateInterface) extends ControllerInterface with Publisher {

  private val undoManager: UndoManager = new UndoManager
  val injector: Injector = Guice.createInjector(new BeadModule)
  val fileIo: FileIoInterface = injector.getInstance(classOf[FileIoInterface])

  def createEmptyTemplate(length: Int, width: Int,stitch:Stitch.Value): Unit = {
    undoManager.doStep(new createTemplateCommand(length,width,stitch,this))
    publish(TemplateChanged(length, width, stitch))
  }

  def tempToString: String = temp.toString

  def setColor(row: Int, col: Int, color: java.awt.Color): Unit = {
    undoManager.doStep(new SetColorCommand(row,col,color,this))
    publish(new BeadChanged)
  }

  def changeSize(length: Int, width: Int): Unit = {
    temp = temp.changeSize(length, width)
    publish(new BeadChanged)
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
    fileIo.save(temp)
    publish(new BeadChanged)
  }

  def load():Unit={
    fileIo.load
    publish(new BeadChanged)
  }

  def bead(row:Int, col:Int): BeadInterface = temp.bead(row,col)
  def isFilled(row:Int, col:Int):Boolean = temp.bead(row, col).isFilled
  def tempLength: Int = temp.size_rows
  def tempWidth: Int = temp.size_cols
}
