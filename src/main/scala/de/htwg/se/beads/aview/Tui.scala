package de.htwg.se.beads.aview

import de.htwg.se.beads.model.{Template, rgbToAnsi, stringToAnsi}

import scala.io.StdIn.readLine

class Tui {

  def processInputSizeLine(input: String, temp: Template): Template = {
    input match {
      case _ => {
        val Array(a, b) = input.split(" ")
        List(a,b).filter(t => t != ' ').map(t => t.toInt) match {

          case row :: column :: Nil => temp.changeSize(row,column)
          case _ => temp
        }
      }
    }
  }

  def processInputLine(input:String, temp:Template):Template={
    var inputSize:String = ""
    input match {
      case "q" => temp
      case "n" => {
        print("Enter Template length and witdth: ")
        inputSize = readLine()
        processInputSizeLine(inputSize,temp)
      }
      case _ => {
        val Array(a, b, c) = input.split(" ")
        val colo = stringToAnsi.colors(c)
        List(a, b).filter(t => t != ' ').map(t => t.toInt) match {
          case row :: column :: Nil => temp.setColor(row, column, rgbToAnsi.colors.map(_.swap)(colo))
          case _ => temp
        }
      }
    }

  }
}

