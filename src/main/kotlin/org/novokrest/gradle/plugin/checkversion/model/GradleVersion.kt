package org.novokrest.gradle.plugin.checkversion.model

data class GradleVersion(
        val major: Int,
        val minor: Int,
        val patch: String
) {
    override fun toString(): String = "$major.$minor.$patch"

    companion object {
        fun parse(version: String): GradleVersion {
            val versionParts = version.split('.')
            check(versionParts.size == 3) {
                "Failed to parse version because of unexpected count of parts: actualPartsCount=${versionParts.size}, version=$version"
            }
            val major = versionParts[0]
            checkNotNull(major.toIntOrNull()) {
                "Failed to parse major version: actualMajor=$major, version=$version"
            }
            val minor = versionParts[1]
            checkNotNull(minor.toIntOrNull()) {
                "Failed to parse minor version: actualMinor=$minor, version=$version"
            }
            return GradleVersion(major.toInt(), minor.toInt(), versionParts[2])
        }
    }
}