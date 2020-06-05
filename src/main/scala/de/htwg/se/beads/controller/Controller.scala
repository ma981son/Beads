package de.htwg.se.beads.controller

import de.htwg.se.beads.model.{Color, Template}
import de.htwg.se.beads.util.Observable

class Controller(var temp: Template) extends Observable {

  def createEmptyTemplate(lenght: Int, width: Int): Unit = {
    temp = new Template(lenght, width)
    notifyObservers
  }

  def TempToString: String = temp.toString

  def setColor(row: Int, col: Int, color: Color): Unit = {
    temp = temp.setColor(row, col, color)
    notifyObservers
  }

  def changeSize(length: Int, width: Int): Unit = {
    temp = temp.changeSize(length, width)
  }
}
