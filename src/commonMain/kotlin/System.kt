import kotlinx.cinterop.refTo
import kotlinx.cinterop.toKString
import okio.FileSystem
import okio.Path
import platform.posix.fgets
import platform.posix.getenv
import platform.posix.pclose
import platform.posix.popen

object Env {
    val home get() = getenv("HOME")?.toKString()
}

fun String.exec(): String? {
    val bufferSize = 1024
    val pointer = popen(this, "r") ?: error("Failed to run command: $this")
    val buffer = ByteArray(bufferSize)
    val stdout = buildString {
        while (buffer.count() < bufferSize) {
            val raw = fgets(buffer.refTo(0), buffer.size, pointer)
            val data = raw?.toKString() ?: break
            append(data)
        }
    }
    val status = pclose(pointer)
    return if (status == 0) stdout else null
}

fun createFile(path: Path) {
    if (FileSystem.SYSTEM.exists(path)) return
    FileSystem.SYSTEM.write(path, mustCreate = true) { writeUtf8("") }
}

fun readFile(path: Path): String =
    FileSystem.SYSTEM.read(path) { readUtf8() }

fun writeFile(path: Path, data: String) =
    FileSystem.SYSTEM.write(path, mustCreate = false) { writeUtf8(data) }
