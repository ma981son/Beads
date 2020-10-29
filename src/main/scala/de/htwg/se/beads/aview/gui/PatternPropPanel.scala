package de.htwg.se.beads.aview.gui

import java.awt.Color.{LIGHT_GRAY, WHITE}
import java.awt.{Color, Dimension}

import de.htwg.se.beads.controller.controllerComponent.ControllerInterface
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.Stitch
import javax.swing.colorchooser.AbstractColorChooserPanel
import javax.swing.event.{ChangeEvent, ChangeListener}
import javax.swing.{JFormattedTextField, JPanel, JSpinner, JTextField, SpinnerNumberModel}

import scala.swing.Swing.LineBorder
import scala.swing.event.{ColorChanged, SelectionChanged}
import scala.swing.{BorderPanel, ColorChooser, ComboBox, Component, GridPanel, Label, Swing, TextField}

class PatternPropPanel(controller: ControllerInterface) extends BorderPanel {

  def restrictHeight(s: Component): Unit = {
    s.maximumSize = new Dimension(Short.MaxValue, s.preferredSize.height)
  }

  def getTextField(spinner: JSpinner): JTextField ={ //TODO Maybe no necessary
    val editor = spinner.getEditor
      editor.asInstanceOf[JSpinner.DefaultEditor].getTextField
  }

  val beadSize = 0.22 //TODO add different beadSizes

  val stitch = new ComboBox(List("Square","Brick")) //TODO "Peyote","3-Net","5-Net"
  stitch.peer.setSelectedItem(stitchMap.map.map(_.swap)(controller.bead(0,0).beadStitch))

  val colTxt = new JSpinner(new SpinnerNumberModel(controller.tempWidth,1,200,1))
  colTxt.setEditor(new JSpinner.NumberEditor(colTxt,"#"))
  val colSize = new TextField((beadSize * controller.tempWidth).toString + "cm",3)
  colSize.editable = false
  val rowTxt = new JSpinner(new SpinnerNumberModel(controller.tempLength,1,200,1))
  colTxt.setEditor(new JSpinner.NumberEditor(colTxt,"#"))
  val rowSize = new TextField((beadSize * controller.tempWidth).toString + "cm",3)
  rowSize.editable = false

  restrictHeight(stitch)

  val propPanel: GridPanel = new GridPanel(3,2){
    contents += new Label("Stitch")
    contents += stitch
    contents += new Label("Columns")
    contents +=  new GridPanel(1,2) {
      contents += Component.wrap(colTxt)
      contents += colSize
      hGap = 2
    }
    contents += new Label("Row")
    contents +=  new GridPanel(1,2) {
      contents += Component.wrap(rowTxt)
      contents += rowSize
      hGap = 2
    }
    hGap = 5
    vGap = 2
    border = Swing.TitledBorder(LineBorder(LIGHT_GRAY), "Pattern Properties")

    listenTo(stitch.selection)
    reactions += {
      case SelectionChanged(`stitch`) =>
        controller.createEmptyTemplate(controller.tempLength,controller.tempWidth,stitchMap.map.apply(stitch.selection.item))
        stitch.peer.setSelectedItem(stitchMap.map.map(_.swap)(controller.bead(0,0).beadStitch))
    }
    val listenerCol: ChangeListener = new ChangeListener() {
      override def stateChanged(e: ChangeEvent): Unit = {
        val txt = colTxt.getValue
        controller.changeSize(controller.tempLength, txt.toString.toInt)
        colSize.peer.setText((beadSize * controller.tempWidth).toString + "cm")
      }
    }

    val listenerRow: ChangeListener = new ChangeListener() {
      override def stateChanged(e: ChangeEvent): Unit = {
        val txt = rowTxt.getValue
        controller.changeSize(txt.toString.toInt, controller.tempWidth)
        rowSize.peer.setText((beadSize * controller.tempLength).toString + "cm")
      }
    }

    colTxt.addChangeListener(listenerCol)
    rowTxt.addChangeListener(listenerRow)
  }

  object selectColor {
    var selColor: Color = WHITE
  }

  def colorChooser: ColorChooser = new ColorChooser(){
    reactions += {
      case ColorChanged(_,c) =>
        selectColor.selColor = c
    }
    border = Swing.TitledBorder(LineBorder(LIGHT_GRAY), "Color")
    peer.setPreviewPanel(new JPanel())
    peer.setDragEnabled(true)
    val oldPanels: Array[AbstractColorChooserPanel] = peer.getChooserPanels
    for (x <- oldPanels.indices){
      val cslName = oldPanels.apply(x).getDisplayName
      if (cslName.equals("HSV")) {
        peer.removeChooserPanel(oldPanels.apply(x))
      }
      if (cslName.equals("HSL")) {
        peer.removeChooserPanel(oldPanels.apply(x))
      }
    }

  }

  add(propPanel,BorderPanel.Position.North)
  add(colorChooser,BorderPanel.Position.Center)

  object stitchMap{
    val map: Map[String, Stitch.Value] = Map("Square" -> Stitch.Square, "Brick" -> Stitch.Brick)
  }

}
