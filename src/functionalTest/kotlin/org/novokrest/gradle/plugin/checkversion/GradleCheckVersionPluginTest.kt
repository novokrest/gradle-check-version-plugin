package org.novokrest.gradle.plugin.checkversion
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Before
import org.junit.Test
import java.io.File

open class GradleCheckVersionPluginTest : AbstractPluginTest() {

    @Before
    fun before() {
        buildFile.writeText("""
            plugins {
                id 'org.novokrest.gradle-check-version'
            }
        """.trimIndent())
    }

    @Test
    fun `should fail build when gradle version in project is not latest`() {
        // when
        val result = GradleRunner.create()
                .withGradleVersion("5.6.4")
                .withProjectDir(testProjectDir.root)
                .withArguments(":help")
                .withPluginClasspath(readMetadataFile())
                .buildAndFail()

        // then
        result.output.contains("Used gradle major version is less than current: project=5, current=6")
    }

    @Test
    fun `should build successfully when gradle version in project is latest`() {
        // when
        val result = GradleRunner.create()
                .withGradleVersion(latestGradleVersion())
                .withProjectDir(testProjectDir.root)
                .withArguments(":help")
                .withPluginClasspath(readMetadataFile())
                .build()

        // then
        result.task(":help")?.outcome == TaskOutcome.SUCCESS
    }

    private fun latestGradleVersion() = GradleVersionProvider().currentVersion().toString()

    private fun readMetadataFile() = PLUGIN_METADATA_FILE.readLines().map { File(it) }

    companion object {
        val PLUGIN_METADATA_FILE: File = File(System.getProperty("PLUGIN_METADATA_FILE") ?: "build/functionalTest/manifest/plugin-classpath.txt")
    }

}
