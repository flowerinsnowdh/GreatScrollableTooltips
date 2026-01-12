plugins {
    alias(libs.plugins.fabric.loom.remap)
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

    maven("https://maven.pkg.github.com/flowerinsnowdh/FlowerinsnowLib") {
        content {
            includeGroup("cn.flowerinsnow.flowerinsnowlib")
        }

        credentials {
            username = "x-access-token"
            password = "${System.getenv("GITHUB_PKG_R_TOKEN")}"
        }
    }

    System.getenv("GRADLE_CENTRAL_MIRROR")?.let {
        maven(it)
    }
    mavenCentral()
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation(libs.fabric.loader)

    modImplementation(libs.fabric.api)

    modImplementation(libs.modmenu)

    include(platform(libs.jackson.bom))
    include(libs.jackson.annotations)
    include(libs.jackson.core)
    include(libs.jackson.databind)

    implementation(platform(libs.flowerinsnowlib.bom))
    include(platform(libs.flowerinsnowlib.bom))
    implementation(libs.flowerinsnowlib.jackson.databind.java11)
    include(libs.flowerinsnowlib.jackson.databind.core)
    include(libs.flowerinsnowlib.jackson.databind.java11)

    compileOnly(libs.jetbrains.annotations)
}

tasks.named<ProcessResources>("processResources").configure {
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
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release = 21
}

tasks.named<Jar>("jar").configure {
    from("../LICENSE")
}
