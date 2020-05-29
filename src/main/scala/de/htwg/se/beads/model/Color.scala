package de.htwg.se.beads.model

case class Color(r:Double, g:Double, b:Double){
  override def toString():String = {
    "Color("+r+","+g+","+b+")"
  }
}
