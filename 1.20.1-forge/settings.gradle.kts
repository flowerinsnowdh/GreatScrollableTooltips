pluginManagement {
    repositories {
        if (System.getenv("USE_MIRROR_REPO") == "true") {
            maven("https://repo.nju.edu.cn/maven/")
        } else {
            gradlePluginPortal()
        }
        maven(url = "https://maven.minecraftforge.net/")
        maven(url = "https://repo.spongepowered.org/repository/maven-public/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}
