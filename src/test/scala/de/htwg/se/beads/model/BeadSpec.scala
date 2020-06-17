package de.htwg.se.beads.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

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
      "have bead Color" in {
        bead.beadColor should be(Color(255, 255, 255))
      }
      "not be filled" in {
        bead.isFilled should be(false)
      }
    }
    "created properly" should {
      val bead = Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))
      "change in Color and" should {
        val coloredBead = bead.changeColor(Color(255, 0, 0))
        "return a new bead with a different Color" in {
          bead.beadColor should be(Color(255, 255, 255))
          coloredBead.beadColor should be(Color(255, 0, 0))
        }
        "be filled" in {
          coloredBead.isFilled should be(true)
        }
      }
      "change in Stitch and" should {
        val changedBead = bead.changeStitch(Stitch.Brick)
        "return a new bead with a different stitch" in {
          bead.beadStitch should be(Stitch.Square)
          changedBead.beadStitch should be(Stitch.Brick)
        }
      }
      "add a right bead and" should {
        val newBead = bead.addBeadRight()
        "return a new bead" in {
          bead should be(Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255)))
          newBead should be(Bead(Coord(1, 0), Stitch.Square, Color(255, 255, 255)))
        }
      }
      "add a left bead and" should {
        val newBead = bead.addBeadLeft()
        "return the new bead" in {
          bead should be(Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255)))
          newBead should be(Bead(Coord(-1, 0), Stitch.Square, Color(255, 255, 255)))
        }
      }
      "add a bead up and" should{
        val newBead = bead.addBeadUp()
        "return the new bead" in {
          bead should be(Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255)))
          newBead should be(Bead(Coord(0, 1), Stitch.Square, Color(255, 255, 255)))
        }
      }
      "be compared and" should {
        val beadtrue = Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255))
        val beadfalse = Bead(Coord(2, 0), Stitch.Square, Color(255, 255, 255))
        "return true if equal" in {
          bead.equals(beadtrue) should be(true)
          bead.equals(beadfalse) should be(false)
          bead.equals(1) should be(false)
        }
      }
      "have a String and" should {
        val string = bead.toString
        "return string" in {
          string should be("|\u001B[47m   \u001B[0m|")
        }
      }
    }
  }
}



