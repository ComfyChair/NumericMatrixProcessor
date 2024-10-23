package processor

import processor.Matrix.TranspositionType
import kotlin.system.exitProcess

fun main() {
    while (true) {
        println("############# Menu #############")
        println("1. Add matrices")
        println("2. Multiply matrix by a constant")
        println("3. Multiply matrices")
        println("4. Transpose matrix")
        println("5. Calculate a determinant")
        println("0. Exit")
        print("Your choice: ")
        when (readln()) {
            "1" -> addMatrices()
            "2" -> scalarMultiplication()
            "3" -> matrixMultiplication()
            "4" -> transposeMatrix()
            "5" -> getDeterminant()
            "0" -> exitProcess(0)
            else -> println("Invalid input. Try again.")
        }
    }
}

fun getDeterminant() {
    println("Enter matrix size:")
    val dims = readDimensions() ?: return
    val matrix = Matrix(dims.first, dims.second)
    if (dims.second != dims.first) {
        println("Invalid input for dimensions $dims: Can only calculate determinant for square matrices.")
        return
    }
    println("Enter matrix:")
    val content = readContent(dims.first) ?: return
    if (!matrix.init(content)) return
    // calculate determinant
    val result = matrix.getDeterminant()
    println(result)
}

fun transposeMatrix() {
    // transposition type menu
    println("\n1. Main diagonal")
    println("2. Side diagonal")
    println("3. Vertical line")
    println("4. Horizontal line")
    print("Your choice: ")
    val type = when (readln()) {
        "1" -> TranspositionType.MAIN_DIAGONAL
        "2" -> TranspositionType.SIDE_DIAGONAL
        "3" -> TranspositionType.VERTICAL
        "4" -> TranspositionType.HORIZONTAL
        else -> {
            println("Invalid input. Try again.")
            return
        }
    }
    // read matrix A
    val matrix = readMatrix("") ?: return
    // transpose
    val result = matrix.transpose(type)
    printMatrix(result)
}

fun matrixMultiplication() {
    val aMatrix = readMatrix("first") ?: return
    val bMatrix = readMatrix("second") ?: return
    if (aMatrix.dims.second != bMatrix.dims.first) {
        println("Invalid input for dimensions i,j=${aMatrix.dims} and k,l=${bMatrix.dims}:" +
                " Dimension j must match dimension k.")
        return
    }
    // calculate
    val result = aMatrix * bMatrix
    printMatrix(result)
}

fun addMatrices() {
    val aMatrix = readMatrix("first") ?: return
    val bMatrix = readMatrix("second") ?: return
    if (aMatrix.dims != bMatrix.dims) {
        println("Invalid input for dimensions ${aMatrix.dims}, ${bMatrix.dims}:" +
                " Dimensions of matrices must match.")
        return
    }
    // calculate and print
    val result = aMatrix + bMatrix
    printMatrix(result)
}

private fun scalarMultiplication() {
    // read matrix A
    val matrix = readMatrix("") ?: return
    // read scalar
    println("Enter constant:")
    val constant = readConstant() ?: return
    // calculate and print
    val result = matrix * constant
    printMatrix(result)

}

fun readMatrix(adj: String) : Matrix? {
    println("Enter size of $adj first matrix:")
    val dims = readDimensions() ?: return null
    val matrix = Matrix(dims.first, dims.second)
    println("Enter $adj matrix:")
    val content = readContent(dims.first) ?: return null
    if (!matrix.init(content)) return null
    return matrix
}

fun readConstant() : Double? {
    val input = readln()
    try {
        return input.toDouble()
    } catch (e: NumberFormatException) {
        println("Invalid input for constant $input: Please provide a number.")
        return null
    }
}

fun readDimensions(): Pair<Int, Int>? {
    val input = readln().split(" ")
    try {
        val asInts = input.map(String::toInt)
        return Pair(asInts[0], asInts[1])
    } catch (e: NumberFormatException) {
        println("Invalid input for dimensions $input: Must be given as integer numbers.")
        return null
    } catch (e: IndexOutOfBoundsException) {
        println("Invalid input for dimensions $input: Please provide two space separated numbers.")
        return null
    }
}

private fun readContent(n: Int): List<List<Double>>? {
    val result = try {
        List(n) { readln().split(" ").map {it.toDouble() }}
    } catch (e: NumberFormatException) {
        println("Invalid input for matrix: Not a number.")
        null
    }
    return result
}

fun printMatrix(result: Matrix) {
    println("The result is:")
    if (result.values.all { row -> row.all { no -> no.compareTo(no.toInt()) == 0} }) {
        // all Doubles are whole numbers -> cast to Int
        println(result.values.joinToString("\n") { row ->
            row.map { it.toInt() }.joinToString(" ") })
    } else {
        println(result.values.joinToString("\n") { it.joinToString(" ") })
    }
}
