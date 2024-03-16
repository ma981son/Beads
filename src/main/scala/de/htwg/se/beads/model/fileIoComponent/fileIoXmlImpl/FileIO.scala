package de.htwg.se.beads.model.fileIoComponent.fileIoXmlImpl
import de.htwg.se.beads.model.fileIoComponent.FileIoInterface
import de.htwg.se.beads.model.templateComponent.TemplateInterface

import scala.xml.{Elem, PrettyPrinter}

class FileIO extends FileIoInterface {

  override def load: TemplateInterface = {
    var temp: TemplateInterface = null
    val file = scala.xml.XML.loadFile("temp.xml")
    val lengthAttr = file \\ "temp" \ "@length"
    val widthAttr = file \\ "temp" \ "@width"
    val length = lengthAttr.text.toInt
    val width = widthAttr.text.toInt
    val beadNodes = file \\ "bead"
    for (bead <- beadNodes) {
      val row: Int = (bead \ "@row").text.toInt
      val col: Int = (bead \ "@col").text.toInt
      //val color: java.awt.Color = bead.text
        //temp = temp.setColor(row, col, color)
    }
    temp
  }

  def save(temp: TemplateInterface): Unit = saveString(temp)

  def saveXML(temp: TemplateInterface): Unit = {
    scala.xml.XML.save("temp.xml", tempToXml(temp))
  }

  def saveString(temp: TemplateInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("temp.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(tempToXml(temp))
    pw.write(xml)
    pw.close()
  }
  def tempToXml(temp: TemplateInterface): Elem = {
    <grid length={ temp.size_rows.toString} width={ temp.size_cols.toString }>
      {
      for {
        row <- 0 until temp.size_rows
        col <- 0 until temp.size_cols
      } yield beadToXml(temp, row, col)
      }
    </grid>
  }

  def beadToXml(temp: TemplateInterface, row: Int, col: Int): Elem = {
    <cell row={row.toString} col={col.toString}>
      {temp.bead(row, col).beadColor}
    </cell>
  }
}
