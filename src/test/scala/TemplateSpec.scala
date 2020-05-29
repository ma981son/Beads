package de.htwg.se.beads.model

import org.scalatest.{Matchers, WordSpec}

class TemplateSpec extends WordSpec with Matchers {
  "A Template is a Square    of Beads. A Template" when{
    "constructed" should {
      "be created with the length and width of its edges as size"{
        val template = new Template(2,3)
      }
      "for test purposes only created with Matrix of beads" in {
        val template = Template(new Matrix(2,3,Bead(Coord(0,0),Stitch.Square,Color(255,255,255))))
        val testTemp = Template(Matrix(Vector(Vector(Bead(Coord(0,0),Stitch.Square,Color(255,255,255))))))
      }
    }
    "created properly but empty" should {
      val template = new Template(2, 2)
      "give access to ist beads" in {
        template.bead(0, 0) should be(Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255)))
        template.bead(1, 0) should be(Bead(Coord(1, 0), Stitch.Square, Color(255, 255, 255)))
      }
      "allow to change the color of its beads" in {
        val changedTemp = template.setColor(0, 0, Color(255, 0, 0))
        changedTemp.bead(0, 0) should be(Bead(Coord(0, 0), Stitch.Square, Color(255, 0, 0)))
        template.bead(0, 0) should be(Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255)))
      }
      "have houses with the right Beads" in {
        template.row(0).beads(0) should be (Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255)))
        template.row(0).beads(1) should be (Bead(Coord(0, 1), Stitch.Square, Color(255, 255, 255)))
        template.row(1).beads(0) should be (Bead(Coord(1, 0), Stitch.Square, Color(255, 255, 255)))
        template.row(1).beads(1) should be (Bead(Coord(1, 1), Stitch.Square, Color(255, 255, 255)))

        template.col(0).beads(0) should be(Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255)))
        template.col(0).beads(1) should be(Bead(Coord(1, 0), Stitch.Square, Color(255, 255, 255)))
        template.col(1).beads(0) should be(Bead(Coord(0, 1), Stitch.Square, Color(255, 255, 255)))
        template.col(1).beads(1) should be(Bead(Coord(1, 1), Stitch.Square, Color(255, 255, 255)))
      }
//      "have a String " in {
//        val stringTemp = template.toString
//        stringTemp should be("\n--------------------------------------------------------\n| Color(255.0,255.0,255.0) || Color(255.0,255.0,255.0) |\n--------------------------------------------------------\n| Color(255.0,255.0,255.0) || Color(255.0,255.0,255.0) |\n--------------------------------------------------------")
//      }
    }

  }
}