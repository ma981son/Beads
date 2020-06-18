package de.htwg.se.beads.controller
import de.htwg.se.beads.model.{Color, Template}
import de.htwg.se.beads.util.Command

class SetColorCommand(row:Int, col:Int, color: Color, controller: Controller) extends Command {
  var memento:Template = controller.temp

  override def doStep(): Unit = controller.temp = controller.temp.setColor(row,col,color)

  override def undoStep(): Unit = {
    val oldcolor = memento.bead(row,col).beadColor
    controller.temp = controller.temp.setColor(row,col,oldcolor)
  }

  override def redoStep(): Unit = controller.temp = controller.temp.setColor(row,col,color)
}
