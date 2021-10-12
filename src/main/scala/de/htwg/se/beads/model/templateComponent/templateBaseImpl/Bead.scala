package de.htwg.se.beads.model.templateComponent.templateBaseImpl


import de.htwg.se.beads.model.templateComponent.BeadInterface

import scala.io.AnsiColor.RESET
import scala.language.postfixOps

object Stitch extends Enumeration {
  val Brick, Square, Fringe, Brazil, Huichol_3, Huichol_5  = Value
}

case class Bead(beadCoord:Coord,
                beadStitch:Stitch.Value,
                beadColor:java.awt.Color) extends BeadInterface{


  def isFilled: Boolean = beadColor != java.awt.Color.LIGHT_GRAY

  def changeColor(color:java.awt.Color): Bead = copy(beadCoord,beadStitch,color)

  def changeStitch(newStitch: Stitch.Value): Bead = copy(beadCoord, newStitch,beadColor)

  override def equals(that: Any): Boolean = that match {
    case that:Bead => that.canEqual(this) && this.beadCoord.equals(that.beadCoord) && this.beadStitch.equals(that.beadStitch) && this.beadColor.equals(that.beadColor)
    case _ => false
  }

  def addBeadRight(): Bead = {Bead(Coord(beadCoord.x + 1, beadCoord.y), beadStitch, beadColor)}

  def addBeadUp(): Bead = {Bead(Coord(beadCoord.x, beadCoord.y + 1),beadStitch, beadColor)}

  def addBeadLeft(): Bead = {Bead(Coord(beadCoord.x - 1, beadCoord.y),beadStitch,beadColor)}

  override def toString: String = {
    if(awtColorToAnsi.colors.contains(beadColor)){
      return s"|${awtColorToAnsi.colors(beadColor)}    $RESET|"
    }
    s"|$beadColor    $RESET|"
  }
}

//object Bead {
//
//  import play.api.libs.json._
//
//  implicit val coordReads: Reads[Coord] = Json.reads[Coord]
//  implicit val coordWrites: Writes[Coord] = Json.writes[Coord]
//
//  implicit val stitchReads: Reads[Stitch.Value] = Reads.enumNameReads(Stitch)
//  val mapper = new ObjectMapper()
//  val module = new SimpleModule()
//  module.addSerializer(classOf[java.awt.Color], ColorJsonSerializer)
//  module.addDeserializer(classOf[java.awt.Color], ColorJsonDeserializer)
//  mapper.registerModule(module)
//
//  implicit val ColorWrites = Writes[java.awt.Color] { c =>
//    JsString("#%02x%02x%02x%02x" format(c.getRed, c.getGreen, c.getBlue, c.getAlpha))
//  }
//
//  implicit val colorReads: Reads[java.awt.Color] = Json.reads[java.awt.Color]
//
//    implicit val beadWrites: Writes[Bead] = (
//      (JsPath \ "beadCoord").write[Coord] and
//        (JsPath \ "beadstitch").write[Stitch.Value] and
//        (JsPath \ "beadColor").write[java.awt.Color]
//      ) (unlift(Bead.unapply))
//
//    implicit val beadReads: Reads[Bead] = (
//      (JsPath \ "beadCoord").read[Coord] and
//        (JsPath \ "beadstitch").read[Stitch.Value] and
//        (JsPath \ "beadColor").read[java.awt.Color]
//      ) (Bead.apply _)
//
//  }
//
//object ColorJsonSerializer extends JsonSerializer[java.awt.Color] {
//  override def serialize(value:java.awt.Color,gen: JsonGenerator,serialize: SerializerProvider): Any ={
//    if(value == null){
//      gen.writeNull()
//      return
//    }
//    gen.writeNumber(value.getRGB)
//  }
//}
//
//object ColorJsonDeserializer extends JsonDeserializer[java.awt.Color]{
//  override def deserialize(p: JsonParser, ctxt: DeserializationContext): awt.Color = new awt.Color(p.getValueAsInt())
//}