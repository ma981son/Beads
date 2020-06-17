package de.htwg.se.beads.controller

import de.htwg.se.beads.model.{Color, Template}
import de.htwg.se.beads.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.language.reflectiveCalls


class ControllerSpec extends AnyWordSpec with Matchers {

  "A Controller" when {
    "observed by an Observer" should {
      val temp = new Template(2, 2)
      val controller = new Controller(temp)
      val observer = new Observer {
        var updated: Boolean = false
        override def update(): Boolean = {updated = true; updated}
      }
      controller.add(observer)
      "notify its Observer after creation" in {
        controller.createEmptyTemplate(2, 2)
        observer.updated should be(true)
        controller.temp.size_rows should be(2)
        controller.temp.size_cols should be(2)
      }
      "notify its Observer after change in size" in {
        controller.changeSize(2, 3)
        observer.updated should be(true)
        controller.temp.size_rows should be(2)
        controller.temp.size_cols should be(3)
      }
      "notify its Observer after changing the color of a bead" in {
        controller.setColor(0, 0, Color(255, 0, 0))
        observer.updated should be(true)
        controller.temp.bead(0, 0).beadColor should be(Color(255, 0, 0))
      }
      "have a String" in {
        controller.tempToString should be("\n---------------" +
          "\n|\u001B[41m   \u001B[0m||\u001B[47m   \u001B[0m||\u001B[47m   \u001B[0m|" +
          "\n---------------" +
          "\n|\u001B[47m   \u001B[0m||\u001B[47m   \u001B[0m||\u001B[47m   \u001B[0m|" +
          "\n---------------\n")
      }
    }
    "empty" should {
      val temp = new Template(2, 2)
      val controller = new Controller(temp)
      "handle undo/redo correctly on an empty undo-stack" in {
        controller.temp.bead(0, 0).isFilled should be(false)
        controller.undo()
        controller.temp.bead(0, 0).isFilled should be(false)
        controller.redo()
        controller.temp.bead(0, 0).isFilled should be(false)
      }
      "handle undo/redo of setting a bead Color correctly" in {
        controller.setColor(0, 0, Color(255, 255, 255))
        controller.temp.bead(0, 0).isFilled should be(false)
        controller.temp.bead(0, 0).beadColor should be(Color(255, 255, 255))
        controller.setColor(0, 0, Color(255, 0, 0))
        controller.temp.bead(0, 0).isFilled should be(true)
        controller.temp.bead(0, 0).beadColor should be(Color(255, 0, 0))
        controller.undo()
        controller.temp.bead(0, 0).isFilled should be(false)
        controller.temp.bead(0, 0).beadColor should be(Color(255, 255, 255))
        controller.redo()
        controller.temp.bead(0, 0).isFilled should be(true)
        controller.temp.bead(0, 0).beadColor should be(Color(255, 0, 0))
      }
    }
  }
}