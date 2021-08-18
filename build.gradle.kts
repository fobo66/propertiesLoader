@file:Suppress("UnstableApiUsage")

plugins {
    `java-gradle-plugin`

    id("org.jetbrains.kotlin.jvm") version "1.5.21"
    id("com.gradle.plugin-publish") version "0.12.0"
}

repositories {
    mavenCentral()
}

version = "1.1"
group = "io.github.fobo66.propertiesloader"

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

pluginBundle {
    website = "https://github.com/fobo66/propertiesLoader"
    vcsUrl = "https://github.com/fobo66/propertiesLoader.git"
    tags = listOf("properties", "config", "extras")
}

gradlePlugin {
    val propertiesLoader by plugins.creating {
        id = "io.github.fobo66.propertiesloader"
        displayName = "PropertiesLoader – load data from .properties files into project extras"
        description = """A plugin that helps you with loading sensitive data like API keys from .properties files into project extras,
            | so you can use these sensitive keys right from ext
        """.trimMargin()
        implementationClass = "io.github.fobo66.PropertiesLoaderPlugin"
    }
}

val functionalTestSourceSet = sourceSets.create("functionalTest") {
}

gradlePlugin.testSourceSets(functionalTestSourceSet)
configurations.getByName("functionalTestImplementation").extendsFrom(configurations.getByName("testImplementation"))

val functionalTest by tasks.creating(Test::class) {
    testClassesDirs = functionalTestSourceSet.output.classesDirs
    classpath = functionalTestSourceSet.runtimeClasspath
}

val check by tasks.getting(Task::class) {
    dependsOn(functionalTest)
}
