package de.htwg.se.beads.controller.controllerComponent.controllerBaseImpl
import com.google.inject.Guice
import com.google.inject.name.Names
import de.htwg.se.beads.model.templateComponent.TemplateInterface
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.{Stitch, Template}
import de.htwg.se.beads.util.Command

class createTemplateCommand(length: Int, width: Int,stitch:Stitch.Value,controller: Controller) extends Command{
  var memento:TemplateInterface = controller.temp

  override def doStep(): Unit = {
    memento = controller.temp
    val newtemp = controller.temp.newTemplate(length,width,stitch)
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

