import java.awt.Color._

import com.fasterxml.jackson.core.{JsonGenerator, JsonParser}
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind._
import de.htwg.se.beads.model.templateComponent.BeadInterface
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.{Bead, Coord, Stitch}
import play.api.libs.functional.syntax._

import scala.language.postfixOps
import scala.language.postfixOps

object Bead {
  import play.api.libs.json._



  implicit val coordReads: Reads[Coord]=Json.reads[Coord]
  implicit val coordWrites: Writes[Coord]= Json.writes[Coord]

  implicit val stitchReads: Reads[Stitch.Value] = Reads.enumNameReads(Stitch)
  val mapper = new ObjectMapper()
  val module = new SimpleModule()
  module.addSerializer(classOf[java.awt.Color], ColorJsonSerializer)
  module.addDeserializer(classOf[java.awt.Color], ColorJsonDeserializer)
  mapper.registerModule(module)

  implicit val colorWrites :Writes[java.awt.Color] = (
    (JsPath\"r").write[Double] and
      (JsPath\"g").write[Double] and
      (JsPath\"b").write[Double] and
      (JsPath\"a").write[Double] and
    )(unlift(java.awt.Color.unapply))

  implicit val colorReads :Reads[java.awt.Color] = (
    (JsPath\"r").read[Double] and
      (JsPath\"g").read[Double] and
      (JsPath\"b").read[Double] and
      (JsPath\"a").read[Double] and
    )(java.awt.Color.apply _)

  implicit val beadWrites : Writes[Bead] = (
    (JsPath\"beadCoord").write[Coord] and
      (JsPath\"beadstitch").write[Stitch.Value] and
      (JsPath\"beadColor").write[java.awt.Color]
    )(unlift(Bead.unapply))

  implicit val beadReads:Reads[Bead] = (
    (JsPath\"beadCoord").read[Coord] and
      (JsPath\"beadstitch").read[Stitch.Value] and
      (JsPath\"beadColor").read[java.awt.Color]
    )(Bead.apply _)

}

object ColorJsonSerializer extends JsonSerializer[java.awt.Color] {
  override def serialize(value:java.awt.Color,gen: JsonGenerator,serialize: SerializerProvider): Any ={
    if(value == null){
      gen.writeNull()
      return
    }
    gen.writeNumber(value.getRGB)
  }
}

object ColorJsonDeserializer extends JsonDeserializer[java.awt.Color]{
  override def deserialize(p: JsonParser, ctxt: DeserializationContext): java.awt.Color = new java.awt.Color(p.getValueAsInt())
}