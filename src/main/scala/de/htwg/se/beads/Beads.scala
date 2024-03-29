package de.htwg.se.beads

import com.google.inject.Guice
import de.htwg.se.beads.aview.Tui
import de.htwg.se.beads.aview.gui.SwingGUI
import de.htwg.se.beads.controller.controllerComponent.ControllerInterface

import scala.io.AnsiColor._
import scala.io.StdIn.readLine

object Beads {
  val injector = Guice.createInjector(new BeadModule)
  val controller = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)
  val gui = new SwingGUI(controller)


  def main(args:Array[String]): Unit = {
    var input:String = ""
    var inputSize:String = ""
    println("Usage: x-coord y-coord color")
    println("Available Colors:")
    print(s"${BLACK}black$RESET, ${RED}red$RESET, ${GREEN}green$RESET, ")
    print(s"${YELLOW}yellow$RESET, ${BLUE}blue$RESET, ${MAGENTA}magenta$RESET, ")
    print(s"${CYAN}cyan$RESET, ${WHITE}white$RESET\n")


//    print("Enter Template length,witdth and stitch: ")
//    inputSize = readLine()
//    tui.processInputSizeLine(inputSize)
//
//    if (args.length>0) input=args(0)
//    if (!input.isEmpty) tui.processInputLine(input)
//    else do{
//      input = readLine()
//      tui.processInputLine(input)
//    }while(input != "q")
  }
}
