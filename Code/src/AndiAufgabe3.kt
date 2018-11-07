@Suppress("NAME_SHADOWING")
fun andiF(n: Long, m: Long): Long {
    val stack = LongStack()

    var n = n
    var m = m

    while (true) {
        when {
            n == 0L -> {
                if (stack.isEmpty()) {
                    return m + 1
                }
                n = stack.pop()
                m += 1
            }
            m == 0L -> {
                n -= 1
                m = 1
            }
            else -> {
                stack.push(n - 1)
                m -= 1
            }
        }
    }
}

fun main(args: Array<String>) {
    for (i in 2..10) {
        println(andiF(4, i.toLong()))
    }
}