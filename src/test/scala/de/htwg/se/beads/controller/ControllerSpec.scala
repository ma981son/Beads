package de.htwg.se.beads.controller

import de.htwg.se.beads.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import java.awt.Color._

import de.htwg.se.beads.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.beads.model.templateComponent.templateBaseImpl.{Color, Stitch, Template}

import scala.language.reflectiveCalls


class ControllerSpec extends AnyWordSpec with Matchers {

  "A Controller" when {
    "empty" should {
      val temp = new Template(2, 2,Stitch.Square)
      val controller = new Controller(temp)
      "handle template size correctly" in {
        controller.createEmptyTemplate(2, 2,Stitch.Square)
        controller.temp.size_rows should be(2)
        controller.temp.size_cols should be(2)
      }
      "change size correctly" in {
        controller.changeSize(2, 3)
        controller.temp.size_rows should be(2)
        controller.temp.size_cols should be(3)
      }
      "change the color of a bead" in {
        controller.setColor(0, 0, WHITE)
        controller.temp.bead(0, 0).beadColor should be(java.awt.Color.WHITE)
      }
      "have a String" in {
        controller.tempToString should be("\n------------------" +
          "\n|\u001B[47m    \u001B[0m||\u001B[47m    \u001B[0m||\u001B[47m    \u001B[0m|" +
          "\n------------------" +
          "\n|\u001B[47m    \u001B[0m||\u001B[47m    \u001B[0m||\u001B[47m    \u001B[0m|" +
          "\n------------------\n")
      }
    }
    "empty" should {
      val temp = new Template(2, 2,Stitch.Square)
      val controller = new Controller(temp)
      "handle undo/redo correctly on an empty undo-stack" in {
        controller.temp.bead(0, 0).isFilled should be(false)
        controller.undo()
        controller.temp.bead(0, 0).isFilled should be(false)
        controller.redo()
        controller.temp.bead(0, 0).isFilled should be(false)
      }
      "handle undo/redo of setting a bead Color correctly" in {
        controller.setColor(0, 0, WHITE)
        controller.temp.bead(0, 0).isFilled should be(false)
        controller.temp.bead(0, 0).beadColor should be(WHITE)
        controller.setColor(0, 0, RED)
        controller.temp.bead(0, 0).isFilled should be(true)
        controller.temp.bead(0, 0).beadColor should be(RED)
        controller.undo()
        controller.temp.bead(0, 0).isFilled should be(false)
        controller.temp.bead(0, 0).beadColor should be(WHITE)
        controller.redo()
        controller.temp.bead(0, 0).isFilled should be(true)
        controller.temp.bead(0, 0).beadColor should be(RED)
      }
    }
  }
}