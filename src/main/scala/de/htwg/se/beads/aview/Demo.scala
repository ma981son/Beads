package de.htwg.se.beads.aview

import de.htwg.se.beads.model.{Bead, Color, Coord, Stitch, Template, rgbToAnsi, stringToAnsi}

import scala.io.AnsiColor._
import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}


object Demo {

   def toInt(s: String): Try[Int] = Try {
       Integer.parseInt(s.trim)
   }

  def main(args: Array[String]) {
    val colors = Map("red" -> RED_B, "white" -> WHITE_B, "blue" -> BLUE_B)
    val second = Map(Color(255, 0, 0) -> RED_B)

    val bead = new Bead(Coord(0, 0), Stitch.Square, Color(255, 0, 0))
    val template = new Template(3,3)
    var input: String = readLine().trim

    val Array(a, b, c) = input.split(" ")
    val colo = stringToAnsi.colors(c)
    List(a,b).filter(t => t != ' ').map(t => t.toInt) match {
      case row :: column :: Nil => template.setColor(row,column,rgbToAnsi.colors.map(_.swap)(colo))
      case _ => template
    }




    println("Available Colors:")
    print(s"${BLACK}black${RESET}, ")
    print(s"${RED}red${RESET}, ")
    print(s"${GREEN}green${RESET}, ")
    print(s"${YELLOW}yellow${RESET}, ")
    print(s"${BLUE}blue${RESET}, ")
    print(s"${MAGENTA}magenta${RESET}, ")
    print(s"${CYAN}cyan${RESET}, ")
    print(s"${WHITE}white${RESET}")



  }
}

