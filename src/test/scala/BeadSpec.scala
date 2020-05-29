package de.htwg.se.beads.model

import org.scalatest.{Matchers, WordSpec}

class BeadSpec extends WordSpec with Matchers {

  "A Bead" when {

    "created" should {
      val bead = Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))
      "have coordinates" in {
        bead.beadCoord should be(Coord(0, 0))
      }
      "have a Square stitch" in {
        bead.beadStitch should be(Stitch.Square)
      }
      "have beadcolor white" in {
        bead.beadColor should be(Color(255, 255, 255))
      }
      "not be filled" in {
        bead.isFilled should be(false)
      }
    }

    "changed in Color" in {
      val coloredBead = Bead(Coord(0, 0), Stitch.Square, Color(255, 0, 0))
      "return that value" in {
        coloredBead.beadColor should be(Color(255, 0, 0))
      }
      "be filled" in {
        coloredBead.isFilled should be(true)
      }
    }

    "changed in Stitch" in {
      val stitchBead = Bead(Coord(0, 0), Stitch.Brick, Color(255, 0, 0))
      "return that value" in {
        stitchBead.beadStitch should be(Stitch.Brick)
      }
    }

    "added a right bead" in {
      val bead = Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))
      val newBead = bead.addBeadRight
      "return the new bead" in {
        newBead should be(Bead(Coord(1, 0), Stitch.Square, Color(255, 255, 255)))
      }
    }

    "added a left bead" in {
      val bead = Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))
      val newBead = bead.addBeadLeft
      "return the new bead" in {
        newBead should be(Bead(Coord(-1, 0), Stitch.Square, Color(255, 255, 255)))
      }
    }

    "added a bead up" in {
      val bead = Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))
      val newBead = bead.addBeadUp
      "return the new bead" in {
        newBead should be(Bead(Coord(0, 1), Stitch.Square, Color(255, 255, 255)))
      }
    }

    "set to String" in {
      val bead = Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))
      val string = bead.toString
      "return string" in {
        string should be("| Color(255.0,255.0,255.0) |")
      }
    }

    "fsfsd" in {

    }

  }
}



