import okio.Path.Companion.toPath

const val credentialsFile = "credentials"

data class Credentials(
    val username: String,
    val password: String,
)

fun readCredentials(dir: String): List<Credentials> {
    val path = "$dir/$credentialsFile".toPath()
    val content = readFile(path)
    val pairs = content.linesAsPairs(separator = " ")
    return pairs.map {
        val (username, password) = it
        Credentials(username, password)
    }
}
