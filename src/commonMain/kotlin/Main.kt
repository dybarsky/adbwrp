import com.github.ajalt.clikt.parameters.options.versionOption

private const val VERSION = "0.1"
private const val FOLDER = ".adbwrp"

fun main(args: Array<String>) {
	runCatching {
		clikt(defaultHome = "${Env.home}/$FOLDER")
			.versionOption(VERSION)
			.main(args)
	}.onFailure {
		println("error: ".red() + it.message)
	}
}
