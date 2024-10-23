package processor

fun main() {
    val aDims = readDimensions()
    val aMatrix = readMatrix(aDims.first)
    val constant = readln().toInt()
    val result = aMatrix * constant
    printMatrix(result)
}


operator fun List<List<Int>>.plus(bMatrix: List<List<Int>>) : List<List<Int>> {
    return List(this.size) { i -> this[i].mapIndexed { j, value ->
        value + bMatrix[i][j]  } }
}

operator fun List<List<Int>>.times(constant: Int) : List<List<Int>> {
    return List(this.size) { i -> this[i].map { value -> value * constant } }
}

fun readDimensions(): Pair<Int, Int> {
    val input = readln().split(" ").map(String::toInt)
    return Pair(input[0], input[1])
}

private fun readMatrix(n: Int) = List(n) { readIntRow() }

private fun readIntRow() = readln().split(" ").map(String::toInt)

fun printMatrix(result: List<List<Int>>) {
    println(result.joinToString("\n") { it.joinToString(" ") })
}
