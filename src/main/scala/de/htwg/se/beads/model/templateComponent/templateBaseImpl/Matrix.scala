package de.htwg.se.beads.model.templateComponent.templateBaseImpl

import scala.collection.immutable.Vector

case class Matrix(matrix:Vector[Vector[Bead]]) {
    def this(row: Int = 0, col: Int = 0, startBead: Bead) =
        this(Vector.tabulate(row, col) { (x, y) => Bead(Coord(x, y), startBead.beadStitch, startBead.beadColor) })

    def replaceBead(row:Int, col:Int, cell:Bead):Matrix =
        copy(matrix.updated(row, matrix(row).updated(col, cell)))

    def replaceRowColor(row:Int, color: java.awt.Color):Matrix =
        copy(matrix.updated(row,matrix(row).map(bead=>bead.changeColor(color))))

    def replaceColumnColor(col:Int, color: java.awt.Color):Matrix =
        copy(matrix.transpose.updated(col,matrix(col).map(bead=>bead.changeColor(color))).transpose)

    def replaceMatrixColor(color: java.awt.Color):Matrix=
        copy(matrix.map(vector=>vector.map(bead=>bead.changeColor(color))))

    val size: (Int,Int) = (matrix.size,matrix.head.size)

    def bead(row:Int, col:Int):Bead = matrix (row)(col)
}
object Matrix {
    import play.api.libs.json._
    implicit val matrixWrites = Json.writes[Matrix]
    implicit val matrixReads = Json.reads[Matrix]
}