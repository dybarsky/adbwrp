import platform.posix.sleep

interface Operation {
    fun execute()
}

class Purge(
    private val packageId: String,
    private val androidHome: String,
) : Operation {
    override fun execute() {
        "$androidHome/platform-tools/adb shell pm uninstall $packageId".exec()
    }
}

class Reset(
    private val packageId: String,
    private val androidHome: String,
) : Operation {
    override fun execute() {
        "$androidHome/platform-tools/adb shell pm clear $packageId".exec()
    }
}

class Start(
    private val packageId: String,
    private val launcher: String,
    private val androidHome: String,
) : Operation {
    override fun execute() {
        "$androidHome/platform-tools/adb shell am start -n $packageId/$launcher 1 > /dev/null".exec()
    }
}

class Creds(
    private val homeDir: String,
    private val prompt: (String) -> String?,
) : Operation {
    override fun execute() {
        val store = readStore(dir = homeDir)
        val creds = readCredentials(dir = homeDir)
        val print = creds.mapIndexed { index, it -> "$index: ${it.username} ${it.password}" }.joinToString("\n")
        val index = prompt(print)?.toIntOrNull() ?: error("Credential not selected")
        val muted = store.toMutableMap().apply { put(storeKeyCreds, "$index") }
        writeStore(homeDir, muted)
    }
}

class Login(
    private val homeDir: String,
    private val androidHome: String,
) : Operation {
    override fun execute() {
        val adb = "$androidHome/platform-tools/adb"
        val store = readStore(dir = homeDir)
        val creds = readCredentials(dir = homeDir)
        val index = store[storeKeyCreds]?.toIntOrNull() ?: error("Select credential first")
        val (username, password) = creds[index]
        // click login button
        "$adb shell input tap 540 2146".exec()
        sleep(1u)
        // click email text field
        "$adb shell input tap 540 1135".exec()
        sleep(1u)
        // input credentials
        "$adb shell input text $username".exec()
        "$adb shell input keyevent 61".exec()
        "$adb shell input text $password".exec()
        // click submit button
        "$adb shell input keyevent 61".exec() // tab
        "$adb shell input keyevent 61".exec() // tab
        "$adb shell input keyevent 66".exec() // enter
    }
}
