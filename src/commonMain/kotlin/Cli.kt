import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option

fun clikt(defaultHome: String): CliktCommand =
    RootCommand(defaultHome).subcommands(config(), start(), purge(), reset(), creds(), login())

private fun start() = object : CliktSubcommand(name = "start", help = Help.start) {
    override fun createOperation(config: Config) = Start(config.appPackage, config.launcherActivity, config.androidSdkHome)
}

private fun purge() = object : CliktSubcommand(name = "purge", help = Help.purge) {
    override fun createOperation(config: Config) = Purge(config.appPackage, config.androidSdkHome)
}

private fun reset() = object : CliktSubcommand(name = "reset", help = Help.reset) {
    override fun createOperation(config: Config) = Reset(config.appPackage, config.androidSdkHome)
}

private fun login() = object : CliktSubcommand(name = "login", help = Help.login) {
    override fun createOperation(config: Config) = Login(config.homeDir, config.androidSdkHome)
}

private fun creds() = object : CliktSubcommand(name = "creds", help = Help.creds) {
    override fun createOperation(config: Config) = Creds(config.homeDir) { prompt(text = it, promptSuffix = "") }
}

private fun config() = object : CliktCommand(name = "config", help = Help.config) {
    override fun run() = echo(Help.configuration)
}

private class RootCommand(defaultHome: String) : CliktCommand(name = "adbwrp", help = Help.description) {
    private val home: String by option(help = Help.home + defaultHome).default(defaultHome)
    override fun run() {
        initStore(dir = home)
        currentContext.obj = readConfig(dir = home)
    }
}

private abstract class CliktSubcommand(
    name: String,
    help: String,
) : CliktCommand(name = name, help = help) {
    private val config: Config by requireObject()
    override fun run() = createOperation(config).execute()
    abstract fun createOperation(config: Config): Operation
}

object Help {
    const val home = "Set home folder. Default is "
    const val start = "Launch application"
    const val purge = "Uninstall application"
    const val reset = "Clean application's data"
    const val creds = "Select active credentials from list"
    const val login = "Emulate user actions to login with active credentials"
    const val config = "Show detailed information about configuration"
    const val description = "Adb Wrapper. Provides high-level operations with application."
    const val configuration = "Application's home folder should contain 'config' and 'credentials' files (can be set with --home)\n" +
        "The 'config' file should contain android_home, app_package, launcher_activity properties\n" +
        "The 'credentials' file should contain pairs of username and password separated by space. Only one pair per line."
}
