package de.htwg.se.beads.aview

import de.htwg.se.beads.controller.Controller
import de.htwg.se.beads.model.{Template, rgbToAnsi, stringToAnsi}
import de.htwg.se.beads.util.Observer

import scala.io.StdIn.readLine

class Tui (controller: Controller) extends Observer{

  controller.add(this)

  def processInputSizeLine(input: String): Unit = {
    input match {
      case _ => {
        val Array(a, b) = input.split(" ")
        List(a, b).filter(t => t != ' ').map(t => t.toInt) match {
          case row :: column :: Nil => controller.changeSize(row, column)
          case _ =>
        }
      }
    }
  }
  def processInputLine(input:String):Unit={
    var inputSize:String = ""
    input match {
      case "q" =>
      case "n" => {
        print("Enter Template length and witdth: ")
        inputSize = readLine()
        processInputSizeLine(inputSize)
      }
      case _ => {
        val Array(a, b, c) = input.split(" ")
        val colo = stringToAnsi.colors(c)
        List(a, b).filter(t => t != ' ').map(t => t.toInt) match {
          case row :: column :: Nil => controller.setColor(row, column, rgbToAnsi.colors.map(_.swap)(colo))
          case _ =>
        }
      }
    }
  }
  override def update: Unit = println(controller.TempToString)
}

