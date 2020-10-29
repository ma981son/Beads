package de.htwg.se.beads.aview.gui

import de.htwg.se.beads.controller.controllerComponent.{BeadChanged, ControllerInterface}

import scala.swing._
import scala.swing.event._


class BeadPanel(row: Int, column: Int, controller: ControllerInterface) extends FlowPanel {


    def myBead = controller.bead(row, column)

    def beadText(row: Int, col: Int) = controller.bead(row, col).toString

    val bead = new Button {
      preferredSize = new Dimension(50, 30)
      border = Swing.BeveledBorder(Swing.Raised)
      foreground = myBead.beadColor
      listenTo(mouse.clicks,mouse.moves,controller)
      reactions += {
        case e: BeadChanged => {
          background = myBead.beadColor
          repaint
        }
      }
    }

    def redraw() = {
      contents.clear()
      //label.text = beadText(row, column)
      foreground = myBead.beadColor
      contents += bead
      repaint
    }
}

