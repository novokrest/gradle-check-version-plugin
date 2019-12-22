package org.novokrest.gradle.plugin.checkversion

import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import java.io.File

abstract class AbstractPluginTest {

    @get:Rule
    var testProjectDir = TemporaryFolder()

    lateinit var buildFile: File

    @Before
    fun setup() {
        buildFile = testProjectDir.newFile("build.gradle")
    }

}