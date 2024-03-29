package io.github.fobo66

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertNotNull


/**
 * Unit tests for the 'io.github.fobo66.propertiesloader' plugin.
 */
class PropertiesLoaderPluginTest {

    private lateinit var project: Project

    @BeforeTest
    fun setUp() {
        project = ProjectBuilder.builder().build()
        project.plugins.apply("io.github.fobo66.propertiesloader")
        project.file("test.properties").writeText("testkey=testvalue")
    }

    @Test
    fun `plugin registers task`() {
        assertNotNull(project.tasks.findByName("loadProperties"))
    }

    @Test
    fun `task loads properties`() {
        project.tasks.getByName("loadProperties", PropertiesLoaderTask::class) {
            propertiesFiles.from(project.file("test.properties"))
        }
        val propertiesLoaderTask = project.tasks.named("loadProperties", PropertiesLoaderTask::class.java).get()

        propertiesLoaderTask.loadProperties()
        assertNotNull(project.extensions.extraProperties["testkey"])
    }

    @Test
    fun `task not loads properties from files that are not properties`() {
        project.file("test.txt").writeText("testkey=testvalue")
        project.tasks.getByName("loadProperties", PropertiesLoaderTask::class) {
            propertiesFiles.from(project.file("test.txt"))
        }
        val propertiesLoaderTask = project.tasks.named("loadProperties", PropertiesLoaderTask::class.java).get()

        propertiesLoaderTask.loadProperties()
        assertFalse(project.extensions.extraProperties.has("testkey"))
    }

    @Test
    fun `task fails when file does not exists`() {
        project.tasks.getByName("loadProperties", PropertiesLoaderTask::class) {
            propertiesFiles.from(project.file("fake.properties"))
        }
        val propertiesLoaderTask = project.tasks.named("loadProperties", PropertiesLoaderTask::class.java).get()

        assertFails {
            propertiesLoaderTask.loadProperties()
        }
    }
}
