package de.htwg.se.beads.controller

import de.htwg.se.beads.model.{Color, Stitch, Template}
import de.htwg.se.beads.util.{Observable, UndoManager}


class Controller(var temp: Template) extends Observable {

  private val undoManager = new UndoManager

  def createEmptyTemplate(length: Int, width: Int,stitch:Stitch.Value): Unit = {
    undoManager.doStep(new createTemplateCommand(length,width,stitch,this))
    notifyObservers()
  }

  def tempToString: String = temp.toString

  def setColor(row: Int, col: Int, color: Color): Unit = {
    undoManager.doStep(new SetColorCommand(row,col,color,this))
    notifyObservers()
  }

  def changeSize(length: Int, width: Int): Unit = {
    temp = temp.changeSize(length, width)
    notifyObservers()
  }

  def changeRowColor(row:Int,color: Color):Unit={
    undoManager.doStep(new changeRowColorCommand(row,color,this))
    notifyObservers()
  }

  def changeColumnColor(col:Int,color: Color):Unit={
    undoManager.doStep(new changeColumnColorCommand(col,color,this))
    notifyObservers()
  }

  def changeTemplateColor(color: Color):Unit={
    undoManager.doStep(new changeTemplateColorCommand(color,this))
    notifyObservers()
  }

  def undo():Unit={
    undoManager.undoStep()
    notifyObservers()
  }

  def redo():Unit={
    undoManager.redoStep()
    notifyObservers()
  }
}
