package de.htwg.se.beads.controller

import de.htwg.se.beads.model.{Stitch, Template}
import de.htwg.se.beads.util.Command

class createTemplateCommand(length: Int, width: Int,stitch:Stitch.Value,controller: Controller) extends Command{
  var memento:Template = controller.temp

  override def doStep(): Unit = {
    memento = controller.temp
    val newtemp = controller.temp.changeTemplate(length,width,stitch)
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

