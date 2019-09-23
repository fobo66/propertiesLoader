package io.github.fobo66

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension
import java.io.FileInputStream
import java.util.*

class PropertiesLoaderPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create("propertiesLoader", PropertiesLoaderExtension::class.java, project)

        project.tasks.register("loadProperties") { task ->
            task.doFirst {
                it.logger.quiet("Starting loading properties from files")
                println("Hello from plugin 'io.github.fobo66.propertiesloader'")
            }
        }
    }

    fun loadProperties(extension: PropertiesLoaderExtension, extras: ExtraPropertiesExtension) {
        extension.propertiesFiles.files.stream()
                .filter { file -> file.name.endsWith(".properties") }
                .map {
                    val properties = Properties()
                    properties.load(FileInputStream(it))
                    return@map properties
                }
                .flatMap {
                    return@flatMap it.entries.stream()
                }
                .forEach {
                    extras.set(it.component1().toString(), it.component2())
                }
    }
}
