package de.htwg.se.beads.util

class UndoManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil
  def doStep(command: Command) = {
    undoStack = command :: undoStack
    command.doStep
  }
  def undoStep(command: Command)={
    undoStack match {
      case Nil =>
      case head::stack => {
        head.undoStep
        undoStack=stack
        redoStack=head::redoStack
      }
    }
  }
  def redoStep(command: Command)={
    redoStack match {
      case Nil =>
      case head::stack => {
        head.redoStep
        redoStack=stack
        undoStack=head::undoStack
      }
    }
  }
}
