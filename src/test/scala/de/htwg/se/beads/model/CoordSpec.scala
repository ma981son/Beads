package de.htwg.se.beads.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CoordSpec extends AnyWordSpec with Matchers {
  "Coordinates" when {
    "created" should {
      val coord = Coord(0,0)
      "have an x coordinate" in {
        coord.x should be (0.0)
      }
      "have an y coordinate" in {
        coord.y should be (0.0)
      }
      "return a String" in {
        coord.toString() should be("0.0,0.0")
      }
    }
  }
}