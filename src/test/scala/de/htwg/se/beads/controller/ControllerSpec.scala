package de.htwg.se.beads.controller

import de.htwg.se.beads.model.{Color, Template}
import de.htwg.se.beads.util.Observer

import scala.language.reflectiveCalls
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers {

  "A Controller" when {
    "observed by an Observer" should {
      val temp = new Template(2,2)
      val controller = new Controller(temp)
      val observer = new Observer {
        var updated: Boolean = false
        def isUpdated: Boolean = updated
        override def update: Unit = updated = true
      }
      controller.add(observer)
      "notify its Observer after creation" in {
        controller.createEmptyTemplate(2,2)
        observer.updated should be(true)
        controller.temp.size_rows should be(2)
        controller.temp.size_cols should be(2)
      }
      "notify its Observer after change in size" in {
        controller.changeSize(2,3)
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
        controller.TempToString should be("\n---------------" +
          "\n|\u001B[41m   \u001B[0m||\u001B[47m   \u001B[0m||\u001B[47m   \u001B[0m|" +
          "\n---------------" +
          "\n|\u001B[47m   \u001B[0m||\u001B[47m   \u001B[0m||\u001B[47m   \u001B[0m|" +
          "\n---------------\n")
      }
    }
  }
}
