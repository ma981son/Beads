package de.htwg.se.beads.aview

import de.htwg.se.beads.controller.Controller
import de.htwg.se.beads.model.{Stitch, rgbToAnsi, stringToAnsi}
import de.htwg.se.beads.util.Observer

import scala.io.StdIn.readLine

class Tui (controller: Controller) extends Observer{

  controller.add(this)

  def processInputSizeLine(input: String): Unit = {
    input match {
      case _ =>
        val Array(a, b, c) = input.split(" ")
        c match {
          case "square" =>
            List(a, b).filter(t => t != ' ').map(t => t.toInt) match {
              case row :: column :: Nil => controller.createEmptyTemplate(row, column, Stitch.Square)
              case _ =>
            }
          case "brick" =>
            List(a, b).filter(t => t != ' ').map(t => t.toInt) match {
              case row :: column :: Nil => controller.createEmptyTemplate(row, column, Stitch.Brick)
              case _ =>
            }
        }
    }
  }

  def processInputLine(input:String):Unit={
    var inputSize:String = ""
    input match {
      case "q" =>
      case "n" =>
        print("Enter Template length,witdth and stitch: ")
        inputSize = readLine()
        processInputSizeLine(inputSize)
      case "z" => controller.undo()
      case "y" => controller.redo()
      case "r" =>
        print("Enter Row and Color: ")
        inputSize = readLine()
        val Array(a, b) = inputSize.split(" ")
        val colo = stringToAnsi.colors(b)
        List(a).filter(t => t != ' ').map(t => t.toInt) match {
          case row :: Nil => controller.changeRowColor(row, rgbToAnsi.colors.map(_.swap)(colo))
          case _ =>
        }
      case "c" =>
        print("Enter Column and Color: ")
        inputSize = readLine()
        val Array(a, b) = inputSize.split(" ")
        val colo = stringToAnsi.colors(b)
        List(a).filter(t => t != ' ').map(t => t.toInt) match {
          case row :: Nil => controller.changeColumnColor(row, rgbToAnsi.colors.map(_.swap)(colo))
          case _ =>
        }
      case "f" =>
        print("Enter Color: ")
        inputSize = readLine()
        val colo = stringToAnsi.colors(inputSize)
        controller.changeTemplateColor(rgbToAnsi.colors.map(_.swap)(colo))
      case _ =>
        val Array(a, b, c) = input.split(" ")
        val colo = stringToAnsi.colors(c)
        List(a, b).filter(t => t != ' ').map(t => t.toInt) match {
          case row :: column :: Nil => controller.setColor(row, column, rgbToAnsi.colors.map(_.swap)(colo))
          case _ =>
        }
    }
  }
  override def update(): Boolean = {
    println(controller.tempToString)
    true
  }
}

