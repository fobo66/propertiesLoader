@file:Suppress("UnstableApiUsage")

plugins {
    // Apply the Java Gradle plugin development plugin to add support for developing Gradle plugins
    `java-gradle-plugin`

    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.3.50"
    id("com.gradle.plugin-publish") version "0.10.0"
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

pluginBundle {
    website = "https://github.com/fobo66/propertiesLoader"
    vcsUrl = "https://github.com/fobo66/propertiesLoader.git"
    tags = listOf("properties", "config", "extras")
}

gradlePlugin {
    // Define the plugin
    val propertiesLoader by plugins.creating {
        id = "io.github.fobo66.propertiesloader"
        displayName = "PropertiesLoader — load data from .properties files into project extras"
        description = """A plugin that helps you with loading sensitive data like API keys from .properties files into project extras,
            | so you can use these sensitive keys right from ext
        """.trimMargin()
        implementationClass = "io.github.fobo66.PropertiesLoaderPlugin"
    }
}

// Add a source set for the functional test suite
val functionalTestSourceSet = sourceSets.create("functionalTest") {
}

gradlePlugin.testSourceSets(functionalTestSourceSet)
configurations.getByName("functionalTestImplementation").extendsFrom(configurations.getByName("testImplementation"))

// Add a task to run the functional tests
val functionalTest by tasks.creating(Test::class) {
    testClassesDirs = functionalTestSourceSet.output.classesDirs
    classpath = functionalTestSourceSet.runtimeClasspath
}

val check by tasks.getting(Task::class) {
    // Run the functional tests as part of `check`
    dependsOn(functionalTest)
}
