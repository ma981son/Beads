package de.htwg.se.beads.aview.gui

import java.awt.Insets

import de.htwg.se.beads.controller.controllerComponent.{BeadChanged, ControllerInterface, TemplateChanged, TemplateSizeChanged}
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.Stitch

import scala.swing.GridBagPanel

class TemplatePanel(controller: ControllerInterface) extends GridBagPanel {

  listenTo(controller)

  def constraints(x: Int, y: Int,
                  gridwidth: Int = 1, gridheight: Int = 1,
                  weightx: Double = 0.0, weighty: Double = 0.0,
                  fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None,
                  anchor: GridBagPanel.Anchor.Value = GridBagPanel.Anchor.Center,
                  insets: Insets = new Insets(0, 0, 0, 0))
  : Constraints = {
    val c = new Constraints
    c.gridx = x
    c.gridy = y
    c.gridwidth = gridwidth
    c.gridheight = gridheight
    c.weightx = weightx
    c.weighty = weighty
    c.fill = fill
    c.anchor = anchor
    c.insets = insets
    c
  }

  var stitch: Stitch.Value = controller.bead(0, 0).beadStitch

  val strategy: Unit = stitch match {
    case Stitch.Brick => brickStrategy()
    case Stitch.Square => squareStrategy
  }

  def brickStrategy(): Unit = {
    for (row <- 0 until controller.tempWidth) {
      for(col <- 0 until controller.tempLength) {
        val bead = new BeadPanel(col, row, controller)
        if (col % 2 == 0) {
          add(bead, constraints(row, col))
        } else {
          if (col == controller.tempWidth) {
            add(bead, constraints(row, col, 2, anchor = GridBagPanel.Anchor.Center, insets = new Insets(0, 0, 0, 10), fill = GridBagPanel.Fill.Horizontal))
          }
            add(bead, constraints(row, col, 2, anchor = GridBagPanel.Anchor.Center, insets = new Insets(0, 10, 0, 10), fill = GridBagPanel.Fill.Horizontal))
        }
        //beads(row)(col) = bead
        listenTo(bead)
        repaint
      }
    }
  }


  def squareStrategy(): Unit = {
    for (row <- 0 until controller.tempWidth) {
      for (col <- 0 until controller.tempLength) {
        val bead = new BeadPanel(col, row, controller)
        add(bead, constraints(row, col))
        //beads(row)(col) = bead
        listenTo(bead)
        repaint
      }
    }
  }

  peer.setVisible(true)

  reactions += {
    case event: TemplateSizeChanged => redrawTemp(event.length,event.width,stitch)
    case event: BeadChanged => repaint
    case event: TemplateChanged => redrawTemp(event.length,event.width,event.stitch)
  }


  def redrawTemp(length: Int, width: Int,newStitch: Stitch.Value): Unit ={
    _contents.clear()
    stitch = newStitch
    val strategy: Unit = stitch match {
      case Stitch.Brick => brickStrategy()
      case Stitch.Square => squareStrategy()
    }
    visible = true
    repaint
  }
}




