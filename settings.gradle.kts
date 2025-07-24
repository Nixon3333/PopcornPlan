pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") // нужен для plugin 'org.jetbrains.kotlin.plugin.compose'
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") // Для compose-multiplatform
    }
}
rootProject.name = "PopcornPlan"

include(":androidApp")
include(":shared")
