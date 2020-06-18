package de.htwg.se.beads.model

import scala.collection.immutable.Vector

case class Matrix(matrix:Vector[Vector[Bead]]) {
    def this(row: Int = 0, col: Int = 0, startBead: Bead) =
        this(Vector.tabulate(row, col) { (x, y) => Bead(Coord(x, y), startBead.beadStitch, startBead.beadColor) })

    def replaceBead(row:Int, col:Int, cell:Bead):Matrix =
        copy(matrix.updated(row, matrix(row).updated(col, cell)))

    def replaceRowColor(row:Int, color: Color):Matrix =
        copy(matrix.updated(row,matrix(row).map(bead=>bead.changeColor(color))))

    def replaceColumnColor(col:Int, color: Color):Matrix =
        copy(matrix.transpose.updated(col,matrix(col).map(bead=>bead.changeColor(color))).transpose)

    def replaceMatrixColor(color: Color):Matrix=
        copy(matrix.map(vector=>vector.map(bead=>bead.changeColor(color))))

    val size: (Int,Int) = (matrix.size,matrix.head.size)

    def bead(row:Int, col:Int):Bead = matrix (row)(col)
}