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
    // read matrix A
    println("Enter size of first matrix:")
    val aDims = readDimensions() ?: return
    val aMatrix = Matrix(aDims.first, aDims.second)
    println("Enter first matrix:")
    val aContent = readMatrix(aDims.first, aDims.second) ?: return
    if (!aMatrix.init(aContent)) return
    // read matrix B
    println("Enter size of second matrix:")
    val bDims = readDimensions() ?: return
    if (aDims.second != bDims.first) {
        println("Invalid input for dimensions i,j=$aDims and k,l=$bDims:" +
                " Dimension j must match dimension k.")
        return
    }
    val bMatrix = Matrix(bDims.first, bDims.second)
    println("Enter second matrix:")
    val bContent = readMatrix(bDims.first, bDims.second) ?: return
    if (!bMatrix.init(bContent)) return
    // calculate
    val result = aMatrix * bMatrix
    printMatrix(result)
}

fun addMatrices() {
    // read matrix A
    println("Enter size of first matrix:")
    val aDims = readDimensions() ?: return
    val aMatrix = Matrix(aDims.first, aDims.second)
    println("Enter first matrix:")
    val aContent = readMatrix(aDims.first, aDims.second) ?: return
    if (!aMatrix.init(aContent)) return
    // read matrix B
    val bDims = readDimensions() ?: return
    println("Enter size of second matrix:")
    if (aDims != bDims) {
        println("Invalid input for dimensions $aDims, $bDims: Dimensions of matrices must match.")
        return
    }
    val bMatrix = Matrix(bDims.first, bDims.second)
    println("Enter second matrix:")
    val bContent = readMatrix(bDims.first, bDims.second) ?: return
    if (!bMatrix.init(bContent)) return
    // calculate and print
    val result = aMatrix + bMatrix
    printMatrix(result)
}

private fun scalarMultiplication() {
    // read matrix A
    println("Enter size of matrix:")
    val aDims = readDimensions() ?: return
    val aMatrix = Matrix(aDims.first, aDims.second)
    println("Enter matrix:")
    val aContent = readMatrix(aDims.first, aDims.second) ?: return
    if (!aMatrix.init(aContent)) return
    // read scalar
    println("Enter constant:")
    val constant = readConstant() ?: return
    // calculate and print
    val result = aMatrix * constant
    printMatrix(result)

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
