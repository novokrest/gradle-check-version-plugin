package org.novokrest.gradle.plugin.checkversion

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.novokrest.gradle.plugin.checkversion.model.GradleVersion

class GradleCheckVersionPlugin: Plugin<Project> {

    private val gradleVersionProvider = GradleVersionProvider()

    override fun apply(project: Project) {
        val projectGradleVersion = GradleVersion.parse(project.gradle.gradleVersion)
        val currentGradleVersion = currentGradleVersion()
        val errors = analyzeGradleVersion(projectGradleVersion, currentGradleVersion)
        errors.forEach { project.logger.warn("Gradle Version Checker: $it") }
    }

    private fun analyzeGradleVersion(projectGradleVersion: GradleVersion, currentGradleVersion: GradleVersion): Collection<String> {
        if (projectGradleVersion.major != currentGradleVersion.major) {
            throw GradleException("Used gradle major version is less than current: project=${projectGradleVersion.major}, current=${currentGradleVersion.major}")
        }
        if (projectGradleVersion.minor != currentGradleVersion.minor) {
            throw GradleException("Used gradle major version is less than current: project=${projectGradleVersion.major}, current=${currentGradleVersion.major}")
        }
        return listOf("User gradle patch version is not actual: project=${projectGradleVersion.patch}, current=${currentGradleVersion.patch}")
    }

    private fun currentGradleVersion(): GradleVersion = gradleVersionProvider.currentVersion()
}
