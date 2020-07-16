package de.htwg.se.beads.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.beads.model.templateComponent.TemplateInterface
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.Template
import de.htwg.se.beads.util.Command

class changeTemplateColorCommand(color: java.awt.Color,controller: Controller) extends Command{
  var memento:TemplateInterface = controller.temp

  override def doStep(): Unit = {
    memento = controller.temp
    val newtemp = controller.temp.changeTemplateColor(color)
    controller.temp = newtemp
  }

  override def undoStep(): Unit = {
    val new_memento = controller.temp
    controller.temp = memento
    memento = new_memento

  }

  override def redoStep(): Unit = {
    val new_memento = controller.temp
    controller.temp = memento
    memento = new_memento
  }
}
