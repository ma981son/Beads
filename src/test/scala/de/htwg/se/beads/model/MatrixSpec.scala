package de.htwg.se.beads.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MatrixSpec extends AnyWordSpec with Matchers {
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
      "replace single beads and return a new data structure" in {
        val returnedMatrix = matrix.replaceBead(0, 0, Bead(Coord(0, 0), Stitch.Square, Color(255, 0, 0)))
        matrix.bead(0, 0) should be(Bead(Coord(0, 0), Stitch.Square, Color(255, 255, 255)))
        returnedMatrix.bead(0, 0) should be(Bead(Coord(0, 0), Stitch.Square, Color(255, 0, 0)))
      }
      "replace a row of beads and return a new data structure" in {
        val returnedMatrix = matrix.replaceRowColor(0,Color(255,0.0,0.0))
        matrix.bead(0,0).beadColor should be(Color(255.0,255.0,255.0))
        matrix.bead(1,0).beadColor should be(Color(255.0,255.0,255.0))
        returnedMatrix.bead(0,0).beadColor should be(Color(255.0,0.0,0.0))
        returnedMatrix.bead(0,1).beadColor should be(Color(255.0,0.0,0.0))
      }
//      "replace a column of beads and return a new data structure" in {
//        val returnedMatrix = matrix.replaceColumnColor(1,Color(255,0.0,0.0))
//        matrix.bead(0,0).beadColor should be(Color(255.0,255.0,255.0))
//        matrix.bead(1,0).beadColor should be(Color(255.0,255.0,255.0))
//        matrix.bead(2,0).beadColor should be(Color(255.0,255.0,255.0))
//        returnedMatrix.bead(0,0).beadColor should be(Color(255.0,0.0,0.0))
//        returnedMatrix.bead(1,0).beadColor should be(Color(255.0,0.0,0.0))
//        returnedMatrix.bead(2,0).beadColor should be(Color(255.0,0.0,0.0))
//      }
      "replace all beads and return a new data structure" in {
        val returnedMatrix = matrix.replaceMatrixColor(Color(255, 0.0, 0.0))
        matrix.bead(0, 0).beadColor should be(Color(255.0, 255.0, 255.0))
        matrix.bead(1, 0).beadColor should be(Color(255.0, 255.0, 255.0))
        matrix.bead(0, 1).beadColor should be(Color(255.0, 255.0, 255.0))
        matrix.bead(1, 1).beadColor should be(Color(255.0, 255.0, 255.0))
        matrix.bead(0, 2).beadColor should be(Color(255.0, 255.0, 255.0))
        matrix.bead(1, 2).beadColor should be(Color(255.0, 255.0, 255.0))
        returnedMatrix.bead(0, 0).beadColor should be(Color(255.0, 0.0, 0.0))
        returnedMatrix.bead(1, 0).beadColor should be(Color(255.0, 0.0, 0.0))
        returnedMatrix.bead(0, 1).beadColor should be(Color(255.0, 0.0, 0.0))
        returnedMatrix.bead(1, 1).beadColor should be(Color(255.0, 0.0, 0.0))
        returnedMatrix.bead(0, 2).beadColor should be(Color(255.0, 0.0, 0.0))
        returnedMatrix.bead(1, 2).beadColor should be(Color(255.0, 0.0, 0.0))
      }
    }
  }
}