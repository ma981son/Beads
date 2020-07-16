package de.htwg.se.beads

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.beads.controller.controllerComponent._
import de.htwg.se.beads.model.fileIoComponent.FileIoInterface
import de.htwg.se.beads.model.templateComponent.{BeadInterface, TemplateInterface}
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.{Bead, Stitch, Template}
import de.htwg.se.beads.model.fileIoComponent.fileIoJsonImpl

class BeadModule extends AbstractModule with ScalaModule {

  override def configure() = {
    bind[TemplateInterface].toInstance(new Template(10,10,Stitch.Square))
    bind[ControllerInterface].to[controllerBaseImpl.Controller]
    bind[FileIoInterface].to[fileIoJsonImpl.FileIO]
  }
}
