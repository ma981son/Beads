package de.htwg.se.beads.model
import de.htwg.se.beads.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.language.reflectiveCalls

import io.AnsiColor._

class ColorSpec extends AnyWordSpec with Matchers {
  "A Color" when {
    "created" should {
      val color = Color(255,255,255)
      "have a ANSI representation" in {
        rgbToAnsi.colors.get(color).get should be(WHITE_B)
      }
      "have a String" in {
        color.toString() should be("Color(255.0,255.0,255.0)")
      }
    }
  }
}
