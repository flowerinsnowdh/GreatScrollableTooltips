pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        System.getenv("GRADLE_CENTRAL_MIRROR")?.let {
            maven(it)
        }
        mavenCentral()
        System.getenv("GRADLE_PLUGIN_PORTAL_MIRROR")?.let {
            maven(it)
        }
        gradlePluginPortal()
    }
}
