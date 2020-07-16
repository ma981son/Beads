package de.htwg.se.beads.aview.gui

import de.htwg.se.beads.controller.controllerComponent.{BeadChanged, ControllerInterface}

import scala.swing._
import scala.swing.event._


class BeadPanel(row: Int, column: Int, controller: ControllerInterface) extends FlowPanel {


    def myBead = controller.bead(row, column)

    def beadText(row: Int, col: Int) = controller.bead(row, col).toString

    val cell = new Button {
      preferredSize = new Dimension(50, 30)
      border = Swing.BeveledBorder(Swing.Raised)
      foreground = myBead.beadColor
      listenTo(mouse.clicks)
      listenTo(controller)
      reactions += {
        case e: BeadChanged => {
          background = myBead.beadColor
          repaint
        }
        case MouseClicked(src, pt, mod, clicks, pops) => {
          controller.setColor(row, column, selectColor.selColor)
          foreground = myBead.beadColor
          repaint
        }
      }
    }

    def redraw() = {
      contents.clear()
      //label.text = beadText(row, column)
      foreground = myBead.beadColor
      contents += cell
      repaint
    }
}

