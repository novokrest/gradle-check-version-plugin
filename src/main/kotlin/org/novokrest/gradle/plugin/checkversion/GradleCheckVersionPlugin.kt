package org.novokrest.gradle.plugin.checkversion

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.novokrest.gradle.plugin.checkversion.model.GradleVersion
import java.util.*
import java.util.Optional.empty

class GradleCheckVersionPlugin: Plugin<Project> {

    private val gradleVersionProvider = GradleVersionProvider()

    override fun apply(project: Project) {
        project.extensions.add(GRADLE_CHECK_VERSION_EXTENSION_NAME, GradleCheckVersionExtension())
        project.afterEvaluate { it.analyzeGradleVersion() }
    }

    private fun Project.analyzeGradleVersion() {
        val projectGradleVersion = GradleVersion.parse(project.gradle.gradleVersion)
        val currentGradleVersion = currentGradleVersion()
        val error = analyzeGradleVersion(projectGradleVersion, currentGradleVersion)

        val extension = extensions.getByType(GradleCheckVersionExtension::class.java)
        error.ifPresent {
            if (extension.throwErrorIfNotLatest) {
                throw GradleException(it)
            } else {
                logger.warn("Gradle Version Checker: $it")
            }
        }
    }

    private fun analyzeGradleVersion(projectGradleVersion: GradleVersion, currentGradleVersion: GradleVersion): Optional<String> {
        if (projectGradleVersion.major != currentGradleVersion.major) {
            return Optional.of("Used gradle major version is less than current: project=${projectGradleVersion.major}, current=${currentGradleVersion.major}")
        }
        if (projectGradleVersion.minor != currentGradleVersion.minor) {
            return Optional.of("Used gradle major version is less than current: project=${projectGradleVersion.major}, current=${currentGradleVersion.major}")
        }
        if (projectGradleVersion.patch != currentGradleVersion.patch) {
            return Optional.of("User gradle patch version is not actual: project=${projectGradleVersion.patch}, current=${currentGradleVersion.patch}")
        }
        return empty()
    }

    private fun currentGradleVersion(): GradleVersion = gradleVersionProvider.currentVersion()

    private companion object {
        const val GRADLE_CHECK_VERSION_EXTENSION_NAME = "gradleCheckVersion"
    }
}
