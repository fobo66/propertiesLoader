package io.github.fobo66

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Functional tests for the 'io.github.fobo66.propertiesloader' plugin.
 */
class PropertiesLoaderPluginFunctionalTest {
    @Test
    fun `can run task`() {
        val projectDir = File("build/functionalTest")
        projectDir.mkdirs()
        projectDir.resolve("settings.gradle").writeText("")
        projectDir.resolve("build.gradle").writeText("""
            plugins {
                id("io.github.fobo66.propertiesloader")
            }
        """)

        val result = prepareBuild(projectDir).build()

        assertNotNull(result.task(":loadProperties"))
    }

    @Test
    fun `task runs successfully`() {
        val projectDir = File("build/functionalTest")
        projectDir.mkdirs()
        projectDir.resolve("settings.gradle").writeText("")
        projectDir.resolve("test.properties").writeText("testkey=testvalue")
        projectDir.resolve("build.gradle").writeText("""
            plugins {
                id("io.github.fobo66.propertiesloader")
            }
            
            propertiesLoader {
                propertiesFiles.from(project.file("test.properties"))
            }
        """)

        val result = prepareBuild(projectDir).build()

        assertEquals(TaskOutcome.SUCCESS, result.task(":loadProperties")?.outcome)
    }

    @Test
    fun `tasks able to use extras`() {
        val projectDir = File("build/functionalTest")
        projectDir.mkdirs()
        projectDir.resolve("settings.gradle").writeText("")
        projectDir.resolve("test.properties").writeText("testkey=testvalue")
        projectDir.resolve("build.gradle").writeText("""
            plugins {
                id("io.github.fobo66.propertiesloader")
            }
            
            propertiesLoader {
                propertiesFiles.from(project.file("test.properties"))
            }
            
            tasks.register("myTask") {
                doLast {
                   
                    print "Loaded prop: " + project.ext.testkey
                }
            }
        """)

        val result = prepareBuild(projectDir, "myTask").build()

        assertTrue {
            result.output.contains("Loaded prop: testvalue")
        }
    }

    @Test
    fun `task fails for non-existing file`() {
        val projectDir = File("build/functionalTest")
        projectDir.mkdirs()
        projectDir.resolve("settings.gradle").writeText("")
        projectDir.resolve("build.gradle").writeText("""
            plugins {
                id("io.github.fobo66.propertiesloader")
            }
            
            propertiesLoader {
                propertiesFiles.from(project.file("fake.properties"))
            }
        """)

        val result = prepareBuild(projectDir).buildAndFail()

        assertEquals(TaskOutcome.FAILED, result.task(":loadProperties")?.outcome)
    }

    @Test
    fun `task skipped when no input files`() {
        val projectDir = File("build/functionalTest")
        projectDir.mkdirs()
        projectDir.resolve("settings.gradle").writeText("")
        projectDir.resolve("build.gradle").writeText("""
            plugins {
                id("io.github.fobo66.propertiesloader")
            }
        """)

        val result = prepareBuild(projectDir).build()

        assertEquals(TaskOutcome.NO_SOURCE, result.task(":loadProperties")?.outcome)
    }

    @Test
    fun `task runs before any other task`() {
        val projectDir = File("build/functionalTest")
        projectDir.mkdirs()
        projectDir.resolve("settings.gradle").writeText("")
        projectDir.resolve("build.gradle").writeText("""
            plugins {
                id("io.github.fobo66.propertiesloader")
            }
            
            tasks.register("myTask1")
            tasks.register("myTask2") {
                dependsOn(":myTask1")
            }
        """)

        val result = prepareBuild(projectDir, "myTask2").build()

        // Verify the result
        assertEquals(TaskOutcome.NO_SOURCE, result.task(":loadProperties")?.outcome)
    }

    private fun prepareBuild(projectDir: File, argument: String = "loadProperties"): GradleRunner {
        return GradleRunner.create()
                .forwardOutput()
                .withPluginClasspath()
                .withArguments(argument)
                .withProjectDir(projectDir)
    }
}
