package de.htwg.se.beads.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import java.awt.Color._

import de.htwg.se.beads.model.templateComponent.templateBaseImpl.{Bead, Coord, Stitch, awtColorToAnsi}

import scala.io.AnsiColor.RESET

class BeadSpec extends AnyWordSpec with Matchers {

  "A Bead" when {

    "created" should {
      val bead = Bead(Coord(0, 0), Stitch.Square, WHITE)

      "have coordinates" in {
        bead.beadCoord should be(Coord(0, 0))
      }
      "have a Stitch" in {
        bead.beadStitch should be(Stitch.Square)
      }
      "have bead Color" in {
        bead.beadColor should be(WHITE)
      }
      "not be filled" in {
        bead.isFilled should be(false)
      }
    }
    "created properly" should {
      val bead = Bead(Coord(0, 0), Stitch.Square, WHITE)
      "change in Color and" should {
        val coloredBead = bead.changeColor(RED)
        "return a new bead with a different Color" in {
          bead.beadColor should be(WHITE)
          coloredBead.beadColor should be(RED)
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
          bead should be(Bead(Coord(0, 0), Stitch.Square, WHITE))
          newBead should be(Bead(Coord(1, 0), Stitch.Square, WHITE))
        }
      }
      "add a left bead and" should {
        val newBead = bead.addBeadLeft()
        "return the new bead" in {
          bead should be(Bead(Coord(0, 0), Stitch.Square, WHITE))
          newBead should be(Bead(Coord(-1, 0), Stitch.Square, WHITE))
        }
      }
      "add a bead up and" should{
        val newBead = bead.addBeadUp()
        "return the new bead" in {
          bead should be(Bead(Coord(0, 0), Stitch.Square, WHITE))
          newBead should be(Bead(Coord(0, 1), Stitch.Square, WHITE))
        }
      }
      "be compared and" should {
        val beadtrue = Bead(Coord(0, 0), Stitch.Square, WHITE)
        val beadfalse = Bead(Coord(2, 0), Stitch.Square, WHITE)
        "return true if equal" in {
          bead.equals(beadtrue) should be(true)
          bead.equals(beadfalse) should be(false)
          bead.equals(1) should be(false)
        }
      }
      "have a String and" should {
        val string = bead.toString
        "return string" in {
          if (awtColorToAnsi.colors.contains(bead.beadColor)) {
            string should be(s"|${awtColorToAnsi.colors(bead.beadColor)}    $RESET|")
          } else {
            string should be(bead.beadColor.toString)
          }
        }
      }
    }
  }
}



