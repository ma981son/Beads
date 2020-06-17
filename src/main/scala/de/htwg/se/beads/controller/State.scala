package de.htwg.se.beads.controller

object State extends Enumeration {
  type State = Value
  val IDLE = Value

  val map = Map[State, String](
    IDLE -> "",
  )

  def message(state:State) = {
    map(state)
  }
}
