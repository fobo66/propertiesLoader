package io.github.fobo66

import org.gradle.api.Plugin
import org.gradle.api.Project

class PropertiesLoaderPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create("propertiesLoader", PropertiesLoaderExtension::class.java, project)

        project.tasks.register<PropertiesLoaderTask>("loadProperties", PropertiesLoaderTask::class.java) { task ->
            task.description = "Load properties from file to project extras"
            task.propertiesFiles.from(extension.propertiesFiles)
        }

        project.tasks.matching { task -> task !is PropertiesLoaderTask }
                .all { task ->
                    task.dependsOn(":loadProperties")
                }
    }
}
