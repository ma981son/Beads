package de.htwg.se.beads.aview.gui

import java.awt.Color._
import java.awt.Dimension

import de.htwg.se.beads.controller.controllerComponent.{BeadChanged, ControllerInterface, TemplateChanged}
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.{Stitch, Template}
import javax.swing.colorchooser.AbstractColorChooserPanel
import javax.swing.{JFrame, JLabel, JPanel, JTextField}

import scala.swing.Swing.LineBorder
import scala.swing._
import scala.swing.event._

class BeadClicked(val row:Int, val column:Int) extends Event

class SwingGUI(controller: ControllerInterface) extends Frame {

  title = "HTWG Beads"

  contents = new BoxPanel(Orientation.Horizontal) {
    contents += new Label("Look at me")
    contents += Swing.HStrut(10)
    contents += Swing.Glue
    contents += new Button("Press me please") {println("thank you")}
    contents += Swing.HStrut(10)
    contents += Swing.Glue

    contents += new PatternPropPanel(controller)

    border = Swing.EmptyBorder(10,10,10,10)
  }

  visible=true
}