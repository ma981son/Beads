package de.htwg.se.beads.model.fileIoComponent.fileIoJsonImpl

import java.awt.Color

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer, JsonSerializer, SerializerProvider}
import de.htwg.se.beads.model.fileIoComponent.FileIoInterface
import de.htwg.se.beads.model.templateComponent.{BeadInterface, TemplateInterface}
import play.api.libs.json._

import scala.io.Source


class FileIO extends FileIoInterface {

  override def load: TemplateInterface = {
    var temp: TemplateInterface = null
    val source: String = Source.fromFile("temp.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val length = (json \ "temp" \ "length").get.toString.toInt
    val width = (json \ "temp" \ "width").get.toString.toInt

    for (index <- 0 until length * width) {
      val row = (json \\ "row") (index).as[Int]
      val col = (json \\ "col") (index).as[Int]
      val bead = (json \\ "bead") (index)

      val color = (bead \ "color").as[java.awt.Color]
      temp = temp.setColor(row, col, color)
    }
    temp
  }

  implicit val ColorWrites = Writes[java.awt.Color] { c =>
    JsString("#%02x%02x%02x" format (c.getRed, c.getGreen, c.getBlue))
  }

  override def save(temp: TemplateInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("temp.json"))
    pw.write(Json.prettyPrint(tempToJson(temp)))
    pw.close
  }

  implicit val beadWrites = new Writes[BeadInterface] {
    def writes(bead: BeadInterface) = Json.obj(
      "color" -> bead.beadColor.toString
    )
  }


  def tempToJson(temp: TemplateInterface) = {
    Json.obj(
      "temp" -> Json.obj(
        "length" -> JsNumber(temp.size_rows),
        "width" -> JsNumber(temp.size_cols),
        "beads" -> Json.toJson(
          for {
            row <- 0 until temp.size_rows;
            col <- 0 until temp.size_cols
          } yield {
            Json.obj(
              "row" -> row,
              "col" -> col,
              "bead" -> Json.toJson(temp.bead(row, col))
            )
          }
        )
      )
    )
  }

  import com.fasterxml.jackson.core.JsonParser
  import javax.swing.tree.TreeNode
  import java.io.IOException


  class ColorSerializer extends JsonSerializer[java.awt.Color] {
    def serialize(value: java.awt.Color, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
      gen.writeStartObject
      gen.writeFieldName("argb")
      gen.writeString(Integer.toHexString(value.getRGB))
      gen.writeEndObject
    }
  }

  class ColorDeserializer extends JsonDeserializer[java.awt.Color] {
    def deserialize(p: JsonParser, ctxt: DeserializationContext): Unit = {
      //val root = p.getCodec.readTree(p).get("argb")
      //val rgba: TextNode = p.getCodec.readTree(p).get("argb").asInstanceOf
      //new Color(Integer.parseUnsignedInt(rgba.textValue, 16), true)
    }
  }
}


