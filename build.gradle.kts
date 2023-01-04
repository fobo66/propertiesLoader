plugins {
    `kotlin-dsl`
    `java-gradle-plugin`

    id("com.gradle.plugin-publish") version "1.1.0"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
}

repositories {
    mavenCentral()
}

version = "1.1"
group = "io.github.fobo66.propertiesloader"

kotlin {
    jvmToolchain {
        version = 17
    }
}

gradlePlugin {
    website.set("https://github.com/fobo66/propertiesLoader")
    vcsUrl.set("https://github.com/fobo66/propertiesLoader.git")
    plugins.register("propertiesLoader") {
        id = "io.github.fobo66.propertiesloader"
        displayName = "PropertiesLoader – load data from .properties files into project extras"
        description =
            """A plugin that helps you with loading sensitive data like API keys from .properties files into project extras,
            | so you can use these sensitive keys right from ext
        """.trimMargin()
        tags.set(listOf("properties", "config", "extras"))
        implementationClass = "io.github.fobo66.PropertiesLoaderPlugin"
    }
}
