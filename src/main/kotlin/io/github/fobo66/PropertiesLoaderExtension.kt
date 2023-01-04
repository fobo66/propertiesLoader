package io.github.fobo66

import org.gradle.api.file.ConfigurableFileCollection

@Suppress("UnnecessaryAbstractClass")
abstract class PropertiesLoaderExtension {
    abstract val propertiesFiles: ConfigurableFileCollection
}
