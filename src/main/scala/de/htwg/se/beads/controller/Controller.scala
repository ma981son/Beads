package de.htwg.se.beads.controller

import de.htwg.se.beads.model.{Color, Template}
import de.htwg.se.beads.util.{Observable,UndoManager}


class Controller(var temp: Template) extends Observable {

  private val undoManager = new UndoManager

  def createEmptyTemplate(length: Int, width: Int): Unit = {
    temp = new Template(length, width)
    notifyObservers()
  }

  def tempToString: String = temp.toString

  def setColor(row: Int, col: Int, color: Color): Unit = {
    undoManager.doStep(new SetCommand(row,col,color,this))
    notifyObservers()
  }

  def changeSize(length: Int, width: Int): Unit = {
    temp = temp.changeSize(length, width)
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
