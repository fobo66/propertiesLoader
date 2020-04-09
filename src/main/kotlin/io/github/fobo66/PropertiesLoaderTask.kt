package io.github.fobo66

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction
import java.io.FileInputStream
import java.util.*
import javax.inject.Inject

@Suppress("UnstableApiUsage")
open class PropertiesLoaderTask @Inject constructor(objects: ObjectFactory) : DefaultTask() {

    @get:InputFiles
    @get:SkipWhenEmpty
    val propertiesFiles: ConfigurableFileCollection = objects.fileCollection()

    @TaskAction
    fun loadProperties() {
        logger.debug("Starting loading properties")
        propertiesFiles.files.stream()
                .filter { file -> file.name.endsWith(".properties") }
                .map {
                    logger.debug("Trying to process properties file ${it.name}")
                    val properties = Properties()
                    properties.load(FileInputStream(it))
                    return@map properties
                }
                .flatMap {
                    logger.debug("Switching to properties' entries")
                    return@flatMap it.entries.stream()
                }
                .forEach { (key, value) ->
                    logger.debug("Adding property to extras")
                    project.extensions.extraProperties.set(key.toString(), value)
                }
    }
}