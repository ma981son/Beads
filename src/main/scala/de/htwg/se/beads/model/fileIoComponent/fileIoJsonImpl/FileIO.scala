package de.htwg.se.beads.model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import de.htwg.se.beads.BeadModule
import de.htwg.se.beads.model.fileIoComponent.FileIoInterface
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.{Color, Coord, Stitch}
import de.htwg.se.beads.model.templateComponent.{BeadInterface, TemplateInterface}
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.Reads._
import play.api.libs.json._

import java.awt
import scala.io.Source


class FileIO extends FileIoInterface {

  override def load: TemplateInterface = {
    var temp: TemplateInterface = null
    val source: String = Source.fromFile("C:\\Users\\Maria\\IdeaProjects\\Beads\\src\\main\\scala\\de\\htwg\\se\\beads\\model\\fileIoComponent\\fileIoJsonImpl\\temp.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val length = (json \ "temp" \ "length").get.toString.toInt
    val width = (json \ "temp" \ "width").get.toString.toInt
    val stitch = (json \ "temp" \ "stitch").as[Stitch.Value]
    val injector = Guice.createInjector(new BeadModule)

    temp = injector.instance[TemplateInterface].newTemplate(length,width,stitch)

    for(index <- 0 until length * width)
    {
      val row = (json \\ "row")(index).as[Int]
      val col = (json \\ "col")(index).as[Int]
      val bead = (json \\ "bead")(index)
      val r = (bead \ "color" \ "r").as[Int]
      val g = (bead \ "color" \ "g").as[Int]
      val b = (bead \ "color" \ "b").as[Int]
      val color = rgbToAWT(Color(r,g,b))
      temp = temp.setColor(row, col, color)
    }
    temp
  }

  def rgbToAWT(color: Color): awt.Color = {
    new awt.Color(color.r.toInt,color.g.toInt,color.b.toInt)
  }

  def awtToRgb(color: awt.Color): Color = {
    Color(color.getRed, color.getGreen, color.getBlue)
  }

  implicit val colorReads: Reads[Color] = (
    (JsPath \ "r").read[Double] and
      (JsPath \ "g").read[Double] and
      (JsPath \ "b").read[Double]
    )(Color.apply _)

  implicit val colorWrites: Writes[Color] = (
    (JsPath \ "r").write[Double] and
      (JsPath \ "g").write[Double] and
      (JsPath \ "b").write[Double]
    )(unlift(Color.unapply))

  implicit val format: Format[Stitch.Value] = Json.formatEnum(Stitch)

  implicit val coordReads: Reads[Coord] = (
    (JsPath \ "x").read[Double] and
      (JsPath \ "y").read[Double]
    )(Coord.apply _)

  implicit val coordWrites: Writes[Coord] = (
    (JsPath \ "x").write[Double] and
      (JsPath \ "y").write[Double]
    )(unlift(Coord.unapply))

  override def save(temp: TemplateInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("C:\\Users\\Maria\\IdeaProjects\\Beads\\src\\main\\scala\\de\\htwg\\se\\beads\\model\\fileIoComponent\\fileIoJsonImpl\\temp.json"))
    pw.write(Json.prettyPrint(tempToJson(temp)))
    pw.close()
  }

  implicit val beadWrites: Writes[BeadInterface] = (bead: BeadInterface) => Json.obj(
    "coords" -> bead.beadCoord,
    "color" -> awtToRgb(bead.beadColor)
  )

  def tempToJson(temp: TemplateInterface): JsValue = {
    temp.toJson
  }

}
