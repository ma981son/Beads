package de.htwg.se.beads

import de.htwg.se.beads.aview.Tui
import de.htwg.se.beads.controller.Controller
import de.htwg.se.beads.model.{Stitch, Template}

import scala.io.AnsiColor._
import scala.io.StdIn.readLine

object Beads {
  val controller = new Controller(new Template(1,6,Stitch.Square))
  val tui = new Tui(controller)
  controller.notifyObservers()

  def main(args:Array[String]): Unit = {
    var input:String = ""
    var inputSize:String = ""
    println("Usage: x-coord y-coord color")
    println("Available Colors:")
    print(s"${BLACK}black$RESET, ${RED}red$RESET, ${GREEN}green$RESET, ")
    print(s"${YELLOW}yellow$RESET, ${BLUE}blue$RESET, ${MAGENTA}magenta$RESET, ")
    print(s"${CYAN}cyan$RESET, ${WHITE}white$RESET\n")


    print("Enter Template length,witdth and stitch: ")
    inputSize = readLine()
    tui.processInputSizeLine(inputSize)

    if (args.length>0) input=args(0)
    if (!input.isEmpty) tui.processInputLine(input)
    else do{
      input = readLine()
      tui.processInputLine(input)
    }while(input != "q")
  }
}
