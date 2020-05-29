package de.htwg.se.beads.aview

import de.htwg.se.beads.model.Template

class Tui {
  def processInputLine(input: String, template: Template):Template = {
    input match {
      case "q" => template
      case "n"=> new Template(4,10)

    }
  }
}