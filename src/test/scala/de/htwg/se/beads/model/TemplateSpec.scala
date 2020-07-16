package de.htwg.se.beads.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import java.awt.Color._

import de.htwg.se.beads.model.templateComponent.templateBaseImpl.{Bead, Coord, Matrix, Stitch, Template}

class TemplateSpec extends AnyWordSpec with Matchers {
  "A Template is a Square    of Beads. A Template" when{
    "constructed" should {
      "be created with the length and width of its edges as size" in {
        val template = new Template(2,3,Stitch.Square)
        template.size_cols should be (3)
        template.size_rows should be (2)
      }
      "for test purposes only created with Matrix of beads" in {
        val template = Template(new Matrix(2,3,Bead(Coord(0,0),Stitch.Square,WHITE)))
        template.size_rows should be (2)
        template.size_cols should be (3)
        val testTemp = Template(Matrix(Vector(Vector(Bead(Coord(0,0),Stitch.Square,WHITE)))))
        testTemp.size_rows should be (1)
        testTemp.size_cols should be (1)
      }
    }
    "created properly but empty" should {
      val template = new Template(2, 2,Stitch.Square)
      "give access to ist beads" in {
        template.bead(0, 0) should be(Bead(Coord(0, 0), Stitch.Square, WHITE))
        template.bead(1, 0) should be(Bead(Coord(1, 0), Stitch.Square, WHITE))
      }
      "allow to change the color of its beads" in {
        val changedTemp = template.setColor(0, 0, RED)
        changedTemp.bead(0, 0) should be(Bead(Coord(0, 0), Stitch.Square, RED))
        template.bead(0, 0) should be(Bead(Coord(0, 0), Stitch.Square, WHITE))
      }
      "have houses with the right Beads" in {
        template.row(0).beads(0) should be (Bead(Coord(0, 0), Stitch.Square, WHITE))
        template.row(0).beads(1) should be (Bead(Coord(0, 1), Stitch.Square, WHITE))
        template.row(1).beads(0) should be (Bead(Coord(1, 0), Stitch.Square, WHITE))
        template.row(1).beads(1) should be (Bead(Coord(1, 1), Stitch.Square, WHITE))

        template.col(0).beads(0) should be(Bead(Coord(0, 0), Stitch.Square, WHITE))
        template.col(0).beads(1) should be(Bead(Coord(1, 0), Stitch.Square, WHITE))
        template.col(1).beads(0) should be(Bead(Coord(0, 1), Stitch.Square, WHITE))
        template.col(1).beads(1) should be(Bead(Coord(1, 1), Stitch.Square, WHITE))
      }
      "when changed in size" in{
        val changedSizeTemp = template.changeSize(3,2)
        changedSizeTemp.size_cols should be(2)
        changedSizeTemp.size_rows should be(3)
        template.size_rows should be(2)
        template.size_cols should be(2)
      }
      "have a String " in {
        val stringTemp = template.toString
        stringTemp should be(
          "\n----------" +
          "\n|\u001B[47m   \u001B[0m||\u001B[47m   \u001B[0m|" +
          "\n----------" +
          "\n|\u001B[47m   \u001B[0m||\u001B[47m   \u001B[0m|" +
          "\n----------\n")
      }
    }

  }
}