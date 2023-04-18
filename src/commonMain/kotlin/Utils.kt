
fun String.linesAsMap(separator: String): Map<String, String> = this
    .split("\n")
    .filter { it.isNotEmpty() }
    .map { it.split(separator) }
    .associate { it[0] to it[1] }

fun String.linesAsPairs(separator: String): List<Pair<String, String>> = this
    .split("\n")
    .filter { it.isNotEmpty() }
    .map { it.split(separator) }
    .map { (first, second) -> first to second }
