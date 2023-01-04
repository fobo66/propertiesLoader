package io.github.fobo66

import java.io.FileInputStream
import java.util.Properties
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction

@CacheableTask
abstract class PropertiesLoaderTask : DefaultTask() {

    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:SkipWhenEmpty
    abstract val propertiesFiles: ConfigurableFileCollection

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
