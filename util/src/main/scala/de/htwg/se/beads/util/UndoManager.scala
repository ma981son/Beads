package de.htwg.se.beads.util

class UndoManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep(command: Command): Unit = {
    undoStack = command :: undoStack
    command.doStep()
  }
  def undoStep(): Unit ={
    undoStack match {
      case Nil =>  //TODO: use Options
      case head::stack =>
        head.undoStep()
        undoStack=stack
        redoStack=head::redoStack
    }
  }
  def redoStep(): Unit ={
    redoStack match {
      case Nil => //Todo: use Options
      case head::stack =>
        head.redoStep()
        redoStack=stack
        undoStack=head::undoStack
    }
  }
}
