package de.htwg.se.beads

import de.htwg.se.beads.model.template.templateBaseImpl.{Bead, Coord, Matrix, Stitch}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.awt.Color._

class MatrixSpec extends AnyWordSpec with Matchers {
  "A Matrix is a tailor-made immutable data type that contains a two-dimentional Vector of Beads. A Matrix" when {
    "empty " should {
      "be created by using a dimention and a sample bead" in {
        val matrix = new Matrix(2, 3, Bead(Coord(0, 0), Stitch.Square, WHITE))
        matrix.size should be((2, 3))
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Matrix(Vector(Vector(Bead(Coord(0, 0), Stitch.Square, WHITE))))
        testMatrix.size should be((1, 1))
      }

    }
    "filled" should {
      val matrix = new Matrix(2, 3, Bead(Coord(0, 0), Stitch.Square, WHITE))
      "give access to its beads" in {
        matrix.bead(1, 0) should be(Bead(Coord(1, 0), Stitch.Square, WHITE))
      }
      "replace single beads and return a new data structure" in {
        val returnedMatrix = matrix.replaceBead(0, 0, Bead(Coord(0, 0), Stitch.Square, RED))
        matrix.bead(0, 0) should be(Bead(Coord(0, 0), Stitch.Square, WHITE))
        returnedMatrix.bead(0, 0) should be(Bead(Coord(0, 0), Stitch.Square, RED))
      }
      "replace a row of beads and return a new data structure" in {
        val returnedMatrix = matrix.replaceRowColor(0,RED)
        matrix.bead(0,0).beadColor should be(WHITE)
        matrix.bead(1,0).beadColor should be(WHITE)
        returnedMatrix.bead(0,0).beadColor should be(RED)
        returnedMatrix.bead(0,1).beadColor should be(RED)
      }
      "replace all beads and return a new data structure" in {
        val returnedMatrix = matrix.replaceMatrixColor(RED)
        matrix.bead(0, 0).beadColor should be(WHITE)
        matrix.bead(1, 0).beadColor should be(WHITE)
        matrix.bead(0, 1).beadColor should be(WHITE)
        matrix.bead(1, 1).beadColor should be(WHITE)
        matrix.bead(0, 2).beadColor should be(WHITE)
        matrix.bead(1, 2).beadColor should be(WHITE)
        returnedMatrix.bead(0, 0).beadColor should be(RED)
        returnedMatrix.bead(1, 0).beadColor should be(RED)
        returnedMatrix.bead(0, 1).beadColor should be(RED)
        returnedMatrix.bead(1, 1).beadColor should be(RED)
        returnedMatrix.bead(0, 2).beadColor should be(RED)
        returnedMatrix.bead(1, 2).beadColor should be(RED)
      }
    }
  }
}