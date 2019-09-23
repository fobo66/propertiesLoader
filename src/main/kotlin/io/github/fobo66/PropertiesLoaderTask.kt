package io.github.fobo66

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction
import java.io.FileInputStream
import java.util.*

class PropertiesLoaderTask @javax.inject.Inject constructor(objects: ObjectFactory) : DefaultTask() {

    @InputFiles
    val propertiesFiles: FileCollection = objects.fileCollection()

    @TaskAction
    fun loadProperties() {
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
                    project.extensions.extraProperties.set(it.component1().toString(), it.component2())
                }
    }
}