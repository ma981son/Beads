package de.htwg.se.beads.aview.gui

import java.awt.Color._
import java.awt.Dimension

import de.htwg.se.beads.controller.controllerComponent.{BeadChanged, ControllerInterface, TemplateChanged}
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.{Stitch, Template}
import javax.swing.{JFrame, JLabel, JTextField}

import scala.swing.Swing.LineBorder
import scala.swing._
import scala.swing.event._

class BeadClicked(val row:Int, val column:Int) extends Event

class SwingGUI(controller: ControllerInterface) extends Frame {
  listenTo(controller)


  //this.preferredSize = new Dimension(1000,600)
  title = "HTWG Sudoku"
  var cells = Array.ofDim[BeadPanel](controller.tempLength, controller.tempWidth)

  def gridPanel1 = new GridPanel(controller.tempLength, controller.tempWidth) {
    this.preferredSize = new Dimension(200, 200)
    border = LineBorder(java.awt.Color.WHITE, 20)
    for {
      outerRow <- 0 until controller.tempLength
      outerColumn <- 0 until controller.tempWidth
    } {
      contents += new GridPanel(controller.tempLength, controller.tempWidth) {
        border = LineBorder(java.awt.Color.WHITE, 1)
        for {
          innerRow <- 0 until controller.tempLength - 1
          innerColumn <- 0 until controller.tempWidth - 1
        } {
          val x = outerRow * controller.tempWidth + innerRow
          val y = outerColumn * controller.tempLength + innerColumn
          val cellPanel = new BeadPanel(x, y, controller)
          cells(x)(y) = cellPanel
          contents += cellPanel
          listenTo(cellPanel)
        }
      }
    }
  }

  def gridPanel = new GridPanel(controller.tempLength,controller.tempWidth) {
    this.preferredSize = new Dimension(controller.tempWidth*50, controller.tempLength*30)
    border = LineBorder(BLACK)

    for {
      outerRow <- 0 until controller.tempLength
      outerColumn <- 0 until controller.tempWidth
    } {
      vGap = 0
      hGap = 0
      val x = outerRow
      val y = outerColumn
      val cellPanel = new BeadPanel(x, y, controller)
      cells(x)(y) = cellPanel
      contents += cellPanel
      listenTo(cellPanel)
    }
  }

  def gridBorder = new BorderPanel {
    this.preferredSize = new Dimension(controller.tempWidth*50, controller.tempLength*30)
    add(gridPanel, BorderPanel.Position.Center)
  }


  def colorChooser = new ColorChooser(){
    border = LineBorder(java.awt.Color.WHITE,20)
    this.preferredSize = new Dimension(500,300)
    reactions += {
      case ColorChanged(_,c) =>
        selectColor.selColor = c
    }
  }

  contents = new BorderPanel {
    add(gridBorder,BorderPanel.Position.Center)
    add(colorChooser,BorderPanel.Position.East)
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") { controller.createEmptyTemplate(controller.tempLength,controller.tempWidth,Stitch.Square) })
      contents += new MenuItem(Action("Quit") { System.exit(0) })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { controller.undo })
      contents += new MenuItem(Action("Redo") { controller.redo })
      contents += new MenuItem(Action("new Square Template 5x5"){controller.createEmptyTemplate(5,5,Stitch.Square)})
      contents += new MenuItem(Action("new Square Template 10x10"){controller.createEmptyTemplate(10,10,Stitch.Square)})
      contents += new MenuItem(Action("new Square Template 20x20"){controller.createEmptyTemplate(20,20,Stitch.Square)})
      contents += new MenuItem(Action("new Square Template 30x30"){controller.createEmptyTemplate(30,30,Stitch.Square)})
      contents += new MenuItem(Action("new Square Template 100x20"){controller.createEmptyTemplate(80,10,Stitch.Square)})
//      contents += new MenuItem(Action("new Brick Template 5x5"){controller.createEmptyTemplate(5,5,Stitch.Brick)})
//      contents += new MenuItem(Action("new Brick Template 10x10"){controller.createEmptyTemplate(10,10,Stitch.Brick)})
//      contents += new MenuItem(Action("new Brick Template 20x20"){controller.createEmptyTemplate(20,20,Stitch.Brick)})
//      contents += new MenuItem(Action("new Brick Template 30x30"){controller.createEmptyTemplate(30,30,Stitch.Brick)})
      contents += new MenuItem(Action("Fill Template"){controller.changeTemplateColor(selectColor.selColor)})
    }
  }

  visible = true
  redraw

  def TemplateDialog(stitch: Stitch.Value) = {


    def top:JFrame = new JFrame {

      val legthlabel = new JLabel("Enter Template Length:")
      val lengthField = new JTextField("20", 5)
      val widthlabel = new JLabel("Enter Template Width:")
      val widthField = new JTextField("20", 5)


      val box = new BoxPanel(Orientation.Vertical) {
        add(legthlabel)
        add(lengthField)
        add(widthlabel)
        add(widthField)
      }

      val pan = Dialog.showConfirmation(box, Dialog.Options.OkCancel)
      if (pan == Dialog.Result.Ok) {
        println("HOLA")
        //            stitch match {
        //              case Stitch.Square =>
        //                List(lengthField.getText, widthField.getText).filter(t => t != ' ').map(t => t.toInt) match {
        //                  case row :: column :: Nil => controller.createEmptyTemplate(row, column, Stitch.Square)
        //                  case _ =>
        //                }
        //              case Stitch.Brick =>
        //                List(lengthField.getText, widthField.getText).filter(t => t != ' ').map(t => t.toInt).filter(t => t > 0) match {
        //                  case row :: column :: Nil => controller.createEmptyTemplate(row, column, Stitch.Brick)
        //                  case _ =>
        //                }
        //            }
        //        }
      }

    }


    //pan.createDialog(top, "Holaa").setVisible(true)


//    val panew = new JPanel {
//      val legthlabel = new JLabel("Enter Template Length:")
//      val lengthField = new JTextField("20", 5)
//      val widthlabel = new JLabel("Enter Template Width:")
//      val widthField = new JTextField("20", 5)
//
//      add(legthlabel)
//      add(lengthField)
//      add(widthlabel)
//      add(widthField)
//
//
//      val pan = new FlowPanel() {
//        val button = new Button("OK")
//        contents += button
//        listenTo(mouse.clicks)
//        reactions += {
//          case e: MouseClicked =>
//            println("HOLA")
//            stitch match {
//              case Stitch.Square =>
//                List(lengthField.getText, widthField.getText).filter(t => t != ' ').map(t => t.toInt) match {
//                  case row :: column :: Nil => controller.createEmptyTemplate(row, column, Stitch.Square)
//                  case _ =>
//                }
//              case Stitch.Brick =>
//                List(lengthField.getText, widthField.getText).filter(t => t != ' ').map(t => t.toInt).filter(t => t > 0) match {
//                  case row :: column :: Nil => controller.createEmptyTemplate(row, column, Stitch.Brick)
//                  case _ =>
//                }
//            }
//        }
//      }
//    }
//    val pane12 = new JOptionPane(panew, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION)
//    pane12.createDialog(null, "Holaa").setVisible(true)


  }

  reactions += {
    case event: BeadChanged     => redraw
    case event: TemplateChanged => redrawTemp(event.length,event.width,event.stitch)
  }

  def redrawTemp(lengt:Int, width:Int, stitch:Stitch.Value) ={
    cells = Array.ofDim[BeadPanel](controller.tempLength,controller.tempWidth)
    contents = new BorderPanel {
      add(gridBorder,BorderPanel.Position.Center)
      add(colorChooser,BorderPanel.Position.East)
    }
    redraw
    repaint
  }

  def redraw = {
    for {
      row <- 0 until controller.tempLength
      column <- 0 until controller.tempWidth
    } cells(row)(column).redraw
    repaint
  }

}
object selectColor {
  var selColor = WHITE
}