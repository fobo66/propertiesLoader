package io.github.fobo66

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.file.FileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.api.tasks.InputFiles
import java.io.FileInputStream
import java.util.Properties
import javax.inject.Inject

class PropertiesLoaderPlugin @Inject constructor(objects: ObjectFactory) : Plugin<Project> {

    @InputFiles
    val propertiesFiles: FileCollection = objects.fileCollection()

    override fun apply(project: Project) {
        project.tasks.register("loadProperties") { task ->
            task.doFirst {
                it.logger.quiet("Starting loading properties from files")
                println("Hello from plugin 'io.github.fobo66.propertiesloader'")
            }
        }
    }

    fun loadProperties(extras: ExtraPropertiesExtension) {
        propertiesFiles.files.stream()
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
