package io.github.fobo66

import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.InputFiles

open class PropertiesLoaderExtension(project: Project) {

    val propertiesFiles: FileCollection

    init {
        propertiesFiles = project.objects.fileCollection()
    }
}