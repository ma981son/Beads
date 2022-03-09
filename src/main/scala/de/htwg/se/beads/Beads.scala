package de.htwg.se.beads

import com.google.inject.{Guice, Injector}
import de.htwg.se.beads.aview.Tui
import de.htwg.se.beads.aview.gui.SwingGUI
import de.htwg.se.beads.controller.ControllerInterface

import scala.io.AnsiColor._

object Beads {
  val injector: Injector = Guice.createInjector(new BeadModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
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
  }
}
