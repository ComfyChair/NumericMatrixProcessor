package processor

import kotlin.system.exitProcess

fun main() {
    while (true) {
        println("############# Menu #############")
        println("1. Add matrices")
        println("2. Multiply matrix by a constant")
        println("3. Multiply matrices")
        println("0. Exit")
        print("Your choice: ")
        when (readln()) {
            "1" -> addMatrices()
            "2" -> scalarMultiplication()
            "3" -> matrixMultiplication()
            "0" -> exitProcess(0)
            else -> println("Invalid input. Try again.")
        }
    }
}

fun matrixMultiplication() {
    TODO("Not yet implemented")
}

fun addMatrices() {
    val aDims = readDimensions() ?: return
    val aMatrix = readMatrix(aDims.first, aDims.second) ?: return
    val bDims = readDimensions() ?: return
    if (aDims != bDims) {
        println("Invalid input for dimensions $aDims, $bDims: Dimensions of matrices must match.")
        return
    }
    val bMatrix = readMatrix(bDims.first, bDims.second) ?: return
    val result = aMatrix + bMatrix
    printMatrix(result)
}

private fun scalarMultiplication() {
    val aDims = readDimensions()
    if (aDims != null) {
        val aMatrix = readMatrix(aDims.first, aDims.second) ?: return
        val constant = readConstant() ?: return
        val result = aMatrix * constant
        printMatrix(result)
    }
}


operator fun List<List<Double>>.plus(bMatrix: List<List<Double>>) : List<List<Double>> {
    return List(this.size) { i -> this@plus[i].mapIndexed { j, value ->
        value + bMatrix[i][j]  } }
}

operator fun List<List<Double>>.times(constant: Double) : List<List<Double>> {
    return List(this.size) { i -> this[i].map { value -> value * constant } }
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

private fun readMatrix(n: Int, m: Int): List<List<Double>>? {
    val matrix = List(n) { readln().split(" ")
        .map(String::toDouble) }
    if (matrix.all { it.size == m }) {
        return matrix
    } else {
        println("Invalid input for matrix: Matrix rows do not correspond to dimension m=$m")
        return null
    }
}

fun printMatrix(result: List<List<Double>>) {
    if (result.all { row -> row.all { no -> no.compareTo(no.toInt()) == 0} }) {
        // all Doubles are whole numbers -> cast to Int
        println(result.joinToString("\n") { row ->
            row.map { it.toInt() }.joinToString(" ") })
    } else {
        println(result.joinToString("\n") { it.joinToString(" ") })
    }
}
