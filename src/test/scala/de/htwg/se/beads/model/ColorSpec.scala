package de.htwg.se.beads.model
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.{Color, rgbToAnsi}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.io.AnsiColor._
import scala.language.reflectiveCalls

class ColorSpec extends AnyWordSpec with Matchers {
  "A Color" when {
    "created" should {
      val color = Color(255,255,255)
      "have a ANSI representation" in {
        rgbToAnsi.colors(color) should be(WHITE_B)
      }
      "have a String" in {
        color.toString() should be("Color(255.0,255.0,255.0)")
      }
    }
  }
}
