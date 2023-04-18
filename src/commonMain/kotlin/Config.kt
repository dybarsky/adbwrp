import okio.Path.Companion.toPath

const val configFile = "config"

data class Config(
    val homeDir: String,
    val appPackage: String,
    val androidSdkHome: String,
    val launcherActivity: String,
)

fun readConfig(dir: String): Config {
    val path = "$dir/$configFile".toPath()
    val content = readFile(path)
    val properties = content.linesAsMap(separator = "=")

    fun Map<String, String>.find(name: String): String =
        get(name)?.takeIf { isNotEmpty() } ?: error("Set $name property into $path file")

    return Config(
        homeDir = dir,
        appPackage = properties.find("app_package"),
        androidSdkHome = properties.find("android_home"),
        launcherActivity = properties.find("launcher_activity"),
    )
}
