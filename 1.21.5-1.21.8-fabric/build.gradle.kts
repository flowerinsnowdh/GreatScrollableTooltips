plugins {
    id("fabric-loom") version "1.13.0-alpha.8"
}

group = "${project.property("maven_group")}"
version = "${project.property("mod_version")}"

base {
    archivesName = project.property("archives_base_name") as String
}

repositories {
    maven("https://maven.pkg.github.com/flowerinsnowdh/GreatScrollableTooltips") {
        credentials {
            username = "x-access-token"
            password = "${System.getenv("GITHUB_TOKEN")}"
        }
    }

    maven("https://maven.terraformersmc.com/releases/")
}

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")

    modImplementation("com.terraformersmc:modmenu:${project.property("modmenu_version")}")

    include(implementation("cn.flowerinsnow.greatscrollabletooltips:common:${project.property("common_module_version")}") as Dependency)
    include(implementation("com.electronwill.night-config:core:${project.property("night_config_version")}") as Dependency)
    include(implementation("com.electronwill.night-config:toml:${project.property("night_config_version")}") as Dependency)
}

tasks.processResources {
    val replaceProperties = mapOf(
        "version" to "${project.version}"
    )
    replaceProperties.forEach(inputs::property)

    filesMatching("fabric.mod.json") {
        expand(replaceProperties)
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.jar {
    from("../LICENSE")
}