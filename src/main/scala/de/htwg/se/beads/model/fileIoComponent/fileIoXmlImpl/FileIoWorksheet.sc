import de.htwg.se.beads.model.fileIoComponent.fileIoXmlImpl
import de.htwg.se.beads.model.fileIoComponent.fileIoXmlImpl.FileIO
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.{Stitch, Template}


object ioWorksheet {
  1 + 2
  val size = 9

  def toXml = {
    <grid size={size.toString}>
    </grid>
  }

  println(toXml)

  val temp = new Template(5,5,Stitch.Square)
  val filledTemp= temp.setColor(0, 0, java.awt.Color.BLACK)
  println(filledTemp.toString)

  val fileIO = new FileIO(filledTemp)
  //val xml = fileIO.beadToXml

}