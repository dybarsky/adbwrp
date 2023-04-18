import okio.Path.Companion.toPath

const val storeFile = "store"
const val storeKeyCreds = "cred"

fun initStore(dir: String) {
    val path = "$dir/$storeFile".toPath()
    createFile(path)
}

fun readStore(dir: String): Map<String, String> {
    val path = "$dir/$storeFile".toPath()
    val content = readFile(path)
    return content.linesAsMap(separator = "=")
}

fun writeStore(dir: String, data: Map<String, String>) {
    val path = "$dir/$storeFile".toPath()
    val content = data.map { (k, v) -> "$k=$v" }.joinToString(separator = "\n")
    writeFile(path, content)
}
