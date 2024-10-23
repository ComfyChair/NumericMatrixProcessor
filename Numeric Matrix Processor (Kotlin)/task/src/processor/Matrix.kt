package processor

class Matrix(private val n: Int, private val m: Int) {
    private lateinit var _values: List<List<Double>>
    val values: List<List<Double>> by lazy { _values }
    enum class TranspositionType { MAIN_DIAGONAL , SIDE_DIAGONAL , VERTICAL, HORIZONTAL }

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

    private constructor(values: List<List<Double>>) : this(values.size, values[0].size) {
        init(values)
    }

    operator fun plus(bMatrix: Matrix) : Matrix {
        return Matrix(List(n) {
            i -> values[i].mapIndexed { j, value ->
            value + bMatrix.values[i][j]  } })
    }

    operator fun times(constant: Double) : Matrix {
        return Matrix(List(n) { i ->
            values[i].map { value -> value * constant } })
    }

    operator fun times(other: Matrix) : Matrix {
        return Matrix(List(n) { i ->  // iterate over A's rows -> new rows
            List(other.m) { j ->  // iterate over B's columns -> fill new row
                values[i].mapIndexed { idx, value ->
                    // iterate over A's ith row and B's jth column
                    // multiply A's row values with B's column values, sum up
                    value * other.values[idx][j]}.sum()
            }
        })
    }

    fun transpose(type: TranspositionType = TranspositionType.MAIN_DIAGONAL) : Matrix {
        when (type) {
            TranspositionType.MAIN_DIAGONAL -> {
                return Matrix(List(m) { i ->
                    List(n) { j -> values[j][i] }
                })
            }
            TranspositionType.SIDE_DIAGONAL -> {
                return Matrix(List(m) { i ->
                    List(n) { j -> values[n-1-j][m-1-i] }
                })
            }
            TranspositionType.HORIZONTAL -> {
                return Matrix(List(n) { i->
                    List(m) { j -> values[n-1-i][j] }
                })
            }
            TranspositionType.VERTICAL -> {
                return Matrix(List(n) { i->
                    List(m) { j -> values[i][m-1-j] }
                })
            }
        }
    }
}