plugins {
    id("net.fabricmc.fabric-loom-remap") version "1.14-SNAPSHOT"
}

group = "${project.property("maven_group")}"
version = "${project.property("mod_version")}"

base {
    archivesName = project.property("archives_base_name") as String
}

repositories {
    maven("https://maven.terraformersmc.com/releases/") {
        content {
            includeModule("com.terraformersmc", "modmenu")
        }
    }

    System.getenv("GRADLE_CENTRAL_MIRROR")?.let {
        maven(it)
    }
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")

    modImplementation("com.terraformersmc:modmenu:${project.property("modmenu_version")}")

    include(implementation("tools.jackson.core:jackson-core:${project.property("jackson_version")}") as Dependency)
    include(implementation("com.fasterxml.jackson.core:jackson-annotations:2.20") as Dependency)
    include(implementation("tools.jackson.core:jackson-databind:${project.property("jackson_version")}") as Dependency)

    compileOnly("org.jetbrains:annotations:${project.property("jetbrains_annotations_version")}")
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