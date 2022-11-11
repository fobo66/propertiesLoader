plugins {
    `kotlin-dsl`
    `java-gradle-plugin`

    kotlin("jvm") version "1.7.20"
    id("com.gradle.plugin-publish") version "1.1.0"
}

repositories {
    mavenCentral()
}

version = "1.1"
group = "io.github.fobo66.propertiesloader"

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

pluginBundle {
    website = "https://github.com/fobo66/propertiesLoader"
    vcsUrl = "https://github.com/fobo66/propertiesLoader.git"
    tags = listOf("properties", "config", "extras")
}

gradlePlugin {
    plugins.register("propertiesLoader") {
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
