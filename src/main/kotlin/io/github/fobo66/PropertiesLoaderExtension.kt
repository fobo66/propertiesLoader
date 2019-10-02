package io.github.fobo66

import org.gradle.api.Project
import org.gradle.api.file.FileCollection

open class PropertiesLoaderExtension(project: Project) {

    val propertiesFiles: FileCollection

    init {
        propertiesFiles = project.objects.fileCollection()
    }
}