package de.htwg.se.beads.aview.gui

import de.htwg.se.beads.controller.BeadChanged
import de.htwg.se.beads.controller.{BeadChanged, ControllerInterface}

import java.awt.Color
import scala.swing.MenuBar.NoMenuBar.contents
import scala.swing._
import scala.swing.event._


class BeadPanel(row: Int, column: Int, controller: ControllerInterface) extends Button {


    def myBead = controller.bead(row, column)

    def beadText = "( " + myBead.beadCoord.x.toInt + ", " + myBead.beadCoord.y.toInt + " )"

      preferredSize = new Dimension(20, 15)
      border = Swing.MatteBorder(1,1,1,1,Color.DARK_GRAY)
      foreground = Color.DARK_GRAY
      //background = myBead.beadColor
      //peer.setText(beadText)
      listenTo(mouse.clicks,mouse.moves,controller)

      reactions += {
        case e: BeadChanged => {
          background = myBead.beadColor
          //repaint
        }
        case MousePressed(src, pt, mod, clicks, pops) => { //TODO implement enable of drag mouse
          controller.setColor(row, column, selectColor.selColor)
        }
      }

    def redraw() = {
      contents.clear()
      background = myBead.beadColor
      contents += this
      repaint
    }
}

