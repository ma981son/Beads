package de.htwg.se.beads.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class BeadSpec extends AnyWordSpec with Matchers {

  "A Bead" when {

    "created" should {
      val bead = Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))

      "have coordinates" in {
        bead.beadCoord should be(Coord(0, 0))
      }
      "have a Stitch" in {
        bead.beadStitch should be(Stitch.Square)
      }
      "have beadcolor" in {
        bead.beadColor should be(Color(255, 255, 255))
      }
      "not be filled" in {
        bead.isFilled should be(false)
      }
    }

    "changed in Color" should {
      val bead = Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))
      val coloredBead = bead.changeColor(Color(255,0,0))
      "return a new bead with a different Color" in {
        bead.beadColor should be (Color(255,255,255))
        coloredBead.beadColor should be(Color(255, 0, 0))
      }
      "be filled" in {
        coloredBead.isFilled should be(true)
      }
    }

    "changed in Stitch" should {
      val bead = Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))
      val changedBead = bead.changeStitch(Stitch.Brick)
      "return a new bead with a different stitch" in {
        bead.beadStitch should be (Stitch.Square)
        changedBead.beadStitch should be(Stitch.Brick)
      }
    }

    "added a right bead" should {
      val bead = Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))
      val newBead = bead.addBeadRight
      "return a new bead" in {
        bead should be (Coord(0, 0), Stitch.Square, Color(255, 255, 255))
        newBead should be(Bead(Coord(1, 0), Stitch.Square, Color(255, 255, 255)))
      }
    }

    "added a left bead" in {
      val bead = Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))
      val newBead = bead.addBeadLeft
      "return the new bead" in {
        bead should be (Coord(0, 0), Stitch.Square, Color(255, 255, 255))
        newBead should be(Bead(Coord(-1, 0), Stitch.Square, Color(255, 255, 255)))
      }
    }

    "added a bead up" in {
      val bead = Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))
      val newBead = bead.addBeadUp
      "return the new bead" in {
        bead should be (Coord(0, 0), Stitch.Square, Color(255, 255, 255))
        newBead should be(Bead(Coord(0, 1), Stitch.Square, Color(255, 255, 255)))
      }
    }

    "have a String" in {
      val bead = Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))
      val string = bead.toString
      "return string" in {
        string should be("| Color(255.0,255.0,255.0) |")
      }
    }
  }
}



