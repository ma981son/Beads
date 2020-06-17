package de.htwg.se.beads.controller
import de.htwg.se.beads.model.Color
import de.htwg.se.beads.util.Command

class SetCommand(row:Int,col:Int, color: Color,controller: Controller) extends Command {
  override def doStep(): Unit = controller.temp = controller.temp.setColor(row,col,color)

  override def undoStep(): Unit = controller.temp = controller.temp.setColor(row,col,color)

  override def redoStep(): Unit = controller.temp = controller.temp.setColor(row,col,color)
}
