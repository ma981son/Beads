package de.htwg.se.beads.aview
import de.htwg.se.beads.controller.Controller
import de.htwg.se.beads.model.{Color, Stitch, Template}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class TuiSpec extends AnyWordSpec with Matchers {

  "A Bead Tui" should {
    val controller = new Controller(new Template(2,2,Stitch.Square))
    val tui = new Tui(controller)
    "create an empty Template on inputSize 3 3" in {
      tui.processInputSizeLine("3 3")
      controller.temp should be(new Template(3,3,Stitch.Square))
    }
    "set a bead color on intput '0 0 red'" in{
      tui.processInputLine("0 0 red")
      controller.temp.bead(0,0).beadColor should be(Color(255,0,0))
    }

  }

}
