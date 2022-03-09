package de.htwg.se.beads.model.templateComponent.templateBaseImpl

import scala.collection.immutable.Vector

case class Matrix(matrix:Vector[Vector[Bead]]) { //No Getter and Setter. Constructor as part of declaration
    def this(row: Int = 0, col: Int = 0, startBead: Bead) =
        this(Vector.tabulate(row, col) { (x, y) => Bead(Coord(x, y), startBead.beadStitch, startBead.beadColor) }) //Use standard collections : Vector

    def replaceBead(row:Int, col:Int, cell:Bead):Matrix =
        copy(matrix.updated(row, matrix(row).updated(col, cell))) //Functional copying -> No state

    def replaceRowColor(row:Int, color: java.awt.Color):Matrix =
        copy(matrix.updated(row,matrix(row).map(bead=>bead.changeColor(color)))) //Functional copying -> No state && Functions as parameter

    def replaceColumnColor(col:Int, color: java.awt.Color):Matrix =
        copy(matrix.transpose.updated(col,matrix(col).map(bead=>bead.changeColor(color))).transpose) //Functional copying -> No state && Functions as parameter

    def replaceMatrixColor(color: java.awt.Color):Matrix=
        copy(matrix.map(vector=>vector.map(bead=>bead.changeColor(color)))) //Functional copying -> No state && Functions as parameter

    val size: (Int,Int) = (matrix.size,matrix.head.size) //No var in model layer. Use val

    def bead(row:Int, col:Int):Bead = matrix (row)(col)
}