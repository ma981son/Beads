package de.htwg.se.beads

import de.htwg.se.beads.aview.Tui
import de.htwg.se.beads.controller.Controller
import de.htwg.se.beads.model.Template

import scala.io.AnsiColor._
import scala.io.StdIn.readLine

object Beads {
  var temp = new Template(10,5)
  val tui = new Tui
  val controller = new Controller(new Template(10,5))
  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args:Array[String]): Unit = {
    var input:String = ""
    var inputSize:String = ""
    println("Usage: x-coord y-coord color")
    println("Available Colors:")
    print(s"${BLACK}black${RESET}, ${RED}red${RESET}, ${GREEN}green${RESET}, ")
    print(s"${YELLOW}yellow${RESET}, ${BLUE}blue${RESET}, ${MAGENTA}magenta${RESET}, ")
    print(s"${CYAN}cyan${RESET}, ${WHITE}white${RESET}\n")


    print("Enter Template length and witdth: ")
    inputSize = readLine()
    temp = tui.processInputSizeLine(inputSize,temp)


    do{
      println("Template: " + temp.toString)
      input = readLine()
      temp = tui.processInputLine(input,temp)
    }while(input != "q")
  }
}
