package processor

class Matrix(private val n: Int, private val m: Int) {
    private lateinit var _values: List<List<Double>>
    val values: List<List<Double>> by lazy { _values }

    fun init(input: List<List<Double>>) : Boolean {
        if (input.size != n) {
            println("Invalid input for dimensions n=$n and matrix content: Content does not match dimensions.")
            return false
        }
        if (input.any { row -> row.size != m }) {
            println("Invalid input for dimensions m=$m and matrix content: Content does not match dimensions.")
            return false
        }
        _values = input
        return true
    }

    operator fun plus(bMatrix: Matrix) : Matrix {
        val resultMatrix = Matrix(n, m)
        resultMatrix.init(List(n) {
            i -> _values[i].mapIndexed { j, value ->
            value + bMatrix._values[i][j]  } })
        return resultMatrix
    }

    operator fun times(constant: Double) : Matrix {
        val resultMatrix = Matrix(n, m)
        resultMatrix.init(List(n) { i ->
            _values[i].map { value -> value * constant } })
        return resultMatrix
    }

    operator fun times(other: Matrix) : Matrix {
        val resultMatrix = Matrix(n, other.m)
        // fill empty matrix
        resultMatrix._values = MutableList(n) { i ->  // iterate over A's rows -> new rows
            MutableList(other.m) { j ->  // iterate over B's columns -> fill new row
                _values[i].mapIndexed { idx, value ->
                    // iterate over A's ith row and B's jth column
                    // multiply A's row values with B's column values, sum up
                    value * other._values[idx][j]}.sum()
            }
        }
        return resultMatrix
    }
}