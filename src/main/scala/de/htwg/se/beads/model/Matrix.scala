package de.htwg.se.beads.model

import scala.collection.immutable.Vector

case class Matrix(matrix:Vector[Vector[Bead]]) {
    def this(row: Int = 0, col: Int = 0, startBead: Bead) =
        this(Vector.tabulate(row, col) { (x, y) => Bead(Coord(x, y), startBead.beadStitch, startBead.beadColor) })

    def replaceBead(row:Int, col:Int, cell:Bead):Matrix =
        copy(matrix.updated(row, matrix(row).updated(col, cell)))

    val size: (Int,Int) = (matrix.size,matrix.head.size)
    def bead(row:Int, col:Int):Bead = matrix (row)(col)
}