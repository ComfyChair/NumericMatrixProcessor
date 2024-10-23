package processor

fun main() {
    val aDims = readDimensions()
    val aMatrix = readMatrix(aDims.first)
    val bDims = readDimensions()
    val bMatrix = readMatrix(bDims.first)
    if (aDims != bDims) {
        println("ERROR")
    } else {
        val result =matrixAddition(aMatrix, bMatrix)
        printMatrix(result)
    }
}

fun printMatrix(result: List<List<Int>>) {
    println(result.joinToString("\n") { it.joinToString(" ") })
}

fun matrixAddition(aMatrix: List<List<Int>>, bMatrix: List<List<Int>>) : List<List<Int>> {
    return List(aMatrix.size) { i -> aMatrix[i].mapIndexed { j, value ->
        value + bMatrix[i][j]  } }
}

fun readDimensions(): Pair<Int, Int> {
    val input = readln().split(" ").map(String::toInt)
    return Pair(input[0], input[1])
}

private fun readMatrix(n: Int) = List(n) { readIntRow() }

private fun readIntRow() = readln().split(" ").map(String::toInt)

