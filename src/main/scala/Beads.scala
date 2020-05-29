package de.htwg.se.beads

import de.htwg.se.beads.model.Template

import scala.io.StdIn.readLine

object Beads {
  def main(args:Array[String]): Unit = {
    var input:String = ""
    var input1:String = ""
    do{
      println("Type Templates length:")
      input = readLine()
      println("Type Templates width:")
      input1 = readLine()

    }while(input != "q")
  }
}