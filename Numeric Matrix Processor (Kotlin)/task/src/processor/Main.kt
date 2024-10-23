package processor

import processor.Matrix.TranspositionType
import kotlin.system.exitProcess

private const val MAIN_MENU = "############# Menu #############\n1. Add matrices\n2. Multiply matrix by a constant\n" +
        "3. Multiply matrices\n4. Transpose matrix\n5. Calculate a determinant\n6. Inverse matrix\n0. Exit"
private const val TRANSPOSE_MENU = "\n1. Main diagonal\n2. Side diagonal\n3. Vertical line\n4. Horizontal line\n"
private const val ENTER = "\nYour choice: "

private const val INVALID_INPUT = "Invalid input. Try again."

fun main() {
    while (true) {
        println(MAIN_MENU + ENTER)
        when (readln()) {
            "1" -> addMatrices()
            "2" -> scalarMultiplication()
            "3" -> matrixMultiplication()
            "4" -> transposeMatrix()
            "5" -> getDeterminant()
            "6" -> invertMatrix()
            "0" -> exitProcess(0)
            else -> println(INVALID_INPUT)
        }
    }
}

fun invertMatrix() {
    val matrix = readMatrix("") ?: return
    if (matrix.dims.first != matrix.dims.second) {
        println("Invalid input: Only square matrices can be inverted.")
    }
    val result = matrix.inverse()
    result?.print() ?: println("No inverse matrix: Determinant is zero.")
}

fun getDeterminant() {
    val matrix = readMatrix("") ?: return
    val result = matrix.getDeterminant()
    println(result)
}

fun transposeMatrix() {
    println(TRANSPOSE_MENU + ENTER)
    val type = when (readln()) {
        "1" -> TranspositionType.MAIN_DIAGONAL
        "2" -> TranspositionType.SIDE_DIAGONAL
        "3" -> TranspositionType.VERTICAL
        "4" -> TranspositionType.HORIZONTAL
        else -> {
            println(INVALID_INPUT)
            return
        }
    }

    val matrix = readMatrix("") ?: return
    val result = matrix.transpose(type)
    result.print()
}

fun matrixMultiplication() {
    val aMatrix = readMatrix(" first") ?: return
    val bMatrix = readMatrix(" second") ?: return
    if (aMatrix.dims.second != bMatrix.dims.first) {
        println("Invalid input for dimensions i,j=${aMatrix.dims} and k,l=${bMatrix.dims}:" +
                " Dimension j must match dimension k.")
        return
    }
    // calculate
    val result = aMatrix * bMatrix
    result.print()
}

fun addMatrices() {
    val aMatrix = readMatrix(" first") ?: return
    val bMatrix = readMatrix(" second") ?: return
    if (aMatrix.dims != bMatrix.dims) {
        println("Invalid input for dimensions ${aMatrix.dims}, ${bMatrix.dims}:" +
                " Dimensions of matrices must match.")
        return
    }
    // calculate and print
    val result = aMatrix + bMatrix
    result.print()
}

private fun scalarMultiplication() {
    // read matrix A
    val matrix = readMatrix("") ?: return
    // read scalar
    println("Enter constant:")
    val constant = readConstant() ?: return
    // calculate and print
    val result = matrix * constant
    result.print()
}

fun readMatrix(adj: String) : Matrix? {
    println("Enter size of$adj matrix:")
    val dims = readDimensions() ?: return null
    val matrix = Matrix(dims.first, dims.second)
    println("Enter $adj matrix:")
    val content = readContent(dims.first) ?: return null
    if (!matrix.init(content)) return null
    return matrix
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

