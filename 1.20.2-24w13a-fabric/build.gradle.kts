plugins {
    id("fabric-loom") version "1.11.0-alpha.19"
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
	archivesName.set(project.property("archives_base_name") as String)
}

repositories {
	mavenCentral()
	maven(url = "https://www.cursemaven.com/")

	maven {
		url = uri("https://maven.pkg.github.com/flowerinsnowdh/GreatScrollableTooltips")
		credentials {
			username = System.getenv("GITHUB_USERNAME")
			password = System.getenv("GITHUB_TOKEN")
		}
	}

	maven(url = "https://maven.terraformersmc.com/releases/")
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
	mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
	modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

	// Fabric API. This is technically optional, but you probably want it anyway.
	modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")
	
	// Uncomment the following line to enable the deprecated Fabric API modules.
	// These are included in the Fabric API production distribution and allow you to update your mod to the latest modules at a later more convenient time.

	// modImplementation "net.fabricmc.fabric-api:fabric-api-deprecated:${project.fabric_version}"

	modImplementation("com.terraformersmc:modmenu:${project.property("modmenu_version")}")
	modImplementation("curse.maven:legendary-tooltips-fabric-542478:4938105")
	include(api("cn.flowerinsnow.greatscrollabletooltips:common:${project.property("common_module_version")}")!!)
	include(api("com.electronwill.night-config:core:${project.property("night_config_version")}")!!)
	include(api("com.electronwill.night-config:toml:${project.property("night_config_version")}")!!)
}

tasks.processResources {
    val replaceProperties: Map<String, Any> = mapOf(
        "version" to project.version
    )
    replaceProperties.forEach(inputs::property)

	filesMatching("fabric.mod.json") {
		expand(replaceProperties)
	}
}

java {
	toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.jar {
	from(file("../LICENSE"))
	from(file("../NOTICE"))
}
