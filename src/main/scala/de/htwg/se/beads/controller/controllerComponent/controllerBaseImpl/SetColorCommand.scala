package de.htwg.se.beads.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.beads.model.templateComponent.TemplateInterface
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.Template
import de.htwg.se.beads.util.Command

class SetColorCommand(row:Int, col:Int, color: java.awt.Color, controller: Controller) extends Command {
  var memento:TemplateInterface = controller.temp

  override def doStep(): Unit = controller.temp = controller.temp.setColor(row,col,color)

  override def undoStep(): Unit = {
    val oldcolor = memento.bead(row,col).beadColor
    controller.temp = controller.temp.setColor(row,col,oldcolor)
  }

  override def redoStep(): Unit = controller.temp = controller.temp.setColor(row,col,color)
}
