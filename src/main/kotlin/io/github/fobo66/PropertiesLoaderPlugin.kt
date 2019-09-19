package io.github.fobo66

import org.gradle.api.Project
import org.gradle.api.Plugin

class PropertiesLoaderPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("loadProperties") { task ->
            task.doLast {
                println("Hello from plugin 'io.github.fobo66.propertiesloader'")
            }
        }
    }
}
