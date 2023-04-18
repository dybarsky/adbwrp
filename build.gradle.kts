plugins {
    kotlin("multiplatform") version "1.8.10"
}

repositories {
    mavenCentral()
}

kotlin {
    linuxX64 {
        binaries {
            executable()
        }
    }
    macosX64 {
        binaries {
            executable()
        }
    }
    macosArm64 {
        binaries {
            executable()
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.squareup.okio:okio:3.3.0")
                implementation("com.github.ajalt.clikt:clikt:3.5.2")
            }
        }
    }
}

tasks["clean"].doLast {
    buildDir.deleteRecursively()
}
