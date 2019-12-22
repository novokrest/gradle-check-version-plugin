package org.novokrest.gradle.plugin.checkversion

import groovy.json.JsonSlurper

class GroovyHttpClient {

    static String get(String url, String responseJsonProperty) {
        new JsonSlurper().parse(url.toURL().bytes)[responseJsonProperty] as String
    }

}
