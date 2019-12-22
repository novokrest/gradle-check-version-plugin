package org.novokrest.gradle.plugin.checkversion

import org.novokrest.gradle.plugin.checkversion.model.GradleVersion

class GradleVersionProvider(private val versionsUrl: String) {

    constructor(): this(GRADLE_VERSIONS_URL)

    fun currentVersion(): GradleVersion = GradleVersion.parse(GroovyHttpClient.get("$versionsUrl/current", "version"))

    private companion object {
        const val GRADLE_VERSIONS_URL = "https://services.gradle.org/versions"
    }

}