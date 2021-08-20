package io.github.fobo66

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

class PropertiesLoaderPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create<PropertiesLoaderExtension>("propertiesLoader", project)

        project.tasks.register<PropertiesLoaderTask>("loadProperties") {
            description = "Load properties from file to project extras"
            propertiesFiles.from(extension.propertiesFiles)
        }

        project.tasks.matching { task -> task !is PropertiesLoaderTask }
            .all {
                dependsOn(":loadProperties")
            }
    }
}
