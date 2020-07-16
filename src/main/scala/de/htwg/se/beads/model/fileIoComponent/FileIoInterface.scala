package de.htwg.se.beads.model.fileIoComponent

import de.htwg.se.beads.model.templateComponent.TemplateInterface

trait FileIoInterface {
  def load: TemplateInterface
  def save(grid: TemplateInterface): Unit
}
