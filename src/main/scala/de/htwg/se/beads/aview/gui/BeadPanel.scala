package de.htwg.se.beads.aview.gui

import de.htwg.se.beads.controller.controllerComponent.{BeadChanged, ControllerInterface}
import de.htwg.se.beads.controller.controllerComponent.controllerBaseImpl.Controller

import scala.swing._
import javax.swing.table._

import scala.swing.event._
import scala.de.htwg.se.beads.aview.gui.selectColor


class BeadPanel(row: Int, column: Int, controller: ControllerInterface) extends FlowPanel {


    def myBead = controller.bead(row, column)

    def beadText(row: Int, col: Int) = controller.bead(row, col).toString

//    var label =
//      new Label {
//        text = beadText(row, column)
//        //font = new Font("Verdana",1,36)
//      }


    val cell = new BoxPanel(Orientation.Vertical) {
     // contents += label
      preferredSize = new Dimension(50, 30)
      border = Swing.BeveledBorder(Swing.Raised)
      background = myBead.beadColor
      listenTo(mouse.clicks)
      listenTo(controller)
      reactions += {
        case e: BeadChanged => {
          background = myBead.beadColor
          repaint
        }
        case MouseClicked(src, pt, mod, clicks, pops) => {
          controller.setColor(row, column, selectColor.selColor)
          background = myBead.beadColor
          repaint
        }
      }
    }

    def redraw() = {
      contents.clear()
      //label.text = beadText(row, column)
      background = myBead.beadColor
      contents += cell
      repaint
    }
}

