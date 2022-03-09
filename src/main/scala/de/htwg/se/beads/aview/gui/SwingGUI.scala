package de.htwg.se.beads.aview.gui

import de.htwg.se.beads.controller.TemplateChanged
import de.htwg.se.beads.controller.{BeadChanged, ControllerInterface, TemplateChanged, TemplateSizeChanged}

import scala.swing._
import scala.swing.event._


class BeadClicked(val row:Int, val column:Int) extends Event

class SwingGUI(controller: ControllerInterface) extends Frame {

  title = "HTWG Beads"

    val tempPanel = new TemplatePanel(controller)
    val patternProp = new PatternPropPanel(controller)

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") {
        controller.createEmptyTemplate(controller.tempLength, controller.tempWidth, controller.stitch)
      })
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") {
        controller.undo
      })
      contents += new MenuItem(Action("Redo") {
        controller.redo
      })
      contents += new MenuItem(Action("Save"){
        controller.save
      })
      contents += new MenuItem(Action("Load"){
        controller.load
      })
    }
  }

  redraw
  visible = true


  reactions += {
    case event: TemplateChanged => redraw
    case event: BeadChanged => redraw
    case event: TemplateSizeChanged => redraw
  }

  def redraw={
    contents = new BoxPanel(Orientation.Horizontal){
      contents += new GridBagPanel{
        def constraints(x: Int, y: Int, gridwidth: Int = 1, gridheight: Int = 1,
            weightx: Double = 0.0, weighty: Double = 0.0,fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None)
      : Constraints = {
        val c = new Constraints
        c.gridx = x
        c.gridy = y
        c.gridwidth = gridwidth
        c.gridheight = gridheight
        c.weightx = weightx
        c.weighty = weighty
        c.fill = fill
        c
      }
        add(tempPanel,constraints(0,0))
      }
      contents += patternProp
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }
    repaint
  }
}