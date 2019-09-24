package io.github.fobo66

import org.gradle.api.Plugin
import org.gradle.api.Project

class PropertiesLoaderPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create("propertiesLoader", PropertiesLoaderExtension::class.java, project)

        project.tasks.register<PropertiesLoaderTask>("loadProperties", PropertiesLoaderTask::class.java) { task ->
            task.propertiesFiles.from(extension.propertiesFiles)

            task.doLast {
                println("Hello from plugin 'io.github.fobo66.propertiesloader'")
            }
        }
    }
}
