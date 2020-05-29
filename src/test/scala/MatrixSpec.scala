package de.htwg.se.beads.model

import org.scalatest.{Matchers, WordSpec}

class MatrixSpec extends WordSpec with Matchers {
  "A Matrix is a tailor-made immutable data type that contains a two-dimentional Vector of Beads. A Matrix" when {
    "empty " should {
      "be created by using a dimention and a sample bead" in {
        val matrix = new Matrix(2, 3, Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255)))
        matrix.size should be((2, 3))
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Matrix(Vector(Vector(Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255)))))
        testMatrix.size should be((1, 1))
      }

    }
    "filled" should {
      val matrix = new Matrix(2, 3, Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255)))
      "give access to its beads" in {
        matrix.bead(1, 0) should be(Bead(Coord(1, 0), Stitch.Square, Color(255, 255, 255)))
      }
      "replace beads and return a new data structure" in {
        val returnedMatrix = matrix.replaceBead(0, 0, Bead(Coord(0, 0), Stitch.Square, Color(255, 0, 0)))
        matrix.bead(0, 0) should be(Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255)))
        returnedMatrix.bead(0, 0) should be(Bead(Coord(0, 0), Stitch.Square, Color(255, 0, 0)))
      }
    }
  }
}