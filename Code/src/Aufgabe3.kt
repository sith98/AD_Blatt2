@Suppress("NON_TAIL_RECURSIVE_CALL")
tailrec fun f(n: Int, m: Int): Int =
    when {
        n == 0 -> m + 1
        m == 0 -> f(n - 1, 1)
        else -> f(n - 1, f(n, m - 1))
    }

class LongStack {
    private var stack = LongArray(10)

    private var size = 0

    fun isEmpty() = size == 0

    fun push(element: Long) {
        if (size >= stack.size) {
            stack = stack.copyOf(stack.size * 2)
        }
        stack[size] = element
        size += 1
    }

    fun peek(): Long = stack[size - 1]
    fun pop() = stack[size -1].also { size -= 1 }
}

@Suppress("NAME_SHADOWING")
fun itF(n: Long, m: Long): Long {
    val stack = LongStack()
    stack.push(n)
    stack.push(m)

    while (true) {
        val m = stack.pop()
        val n = stack.pop()

        when {
            n == 0L -> {
                val result = m + 1
                if (stack.isEmpty()) {
                    return result
                }
                // set new m
                stack.push(result)
            }
            m == 0L -> {
                stack.push(n - 1) // new n
                stack.push(1)     // new m
            }
            else -> {
                // current step
                stack.push(n - 1)

                // recursion for calculating parameter m
                stack.push(n)
                stack.push(m - 1)
            }
        }
    }
}

fun main(args: Array<String>) {
    // f(0, m) ist trivial
    for (i in 2L..30L) {
        println(itF(4, i))
    }


//    println(itF(4, 1)) // 65533
//    println(itF(5, 0)) // 65533

}