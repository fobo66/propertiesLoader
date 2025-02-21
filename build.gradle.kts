import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`

    id("com.gradle.plugin-publish") version "1.3.1"
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
}

repositories {
    mavenCentral()
}

version = "1.1"
group = "io.github.fobo66.propertiesloader"

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest()

            dependencies {
                implementation(project())
            }
        }

        // Create a new test suite
        val functionalTest by registering(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest()

            dependencies {
                implementation(project())
            }

            targets {
                all {
                    // This test suite should run after the built-in test suite has run its tests
                    testTask.configure { shouldRunAfter(test) }
                }
            }
        }
    }
}
