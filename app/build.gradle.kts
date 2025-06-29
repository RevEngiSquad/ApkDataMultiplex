/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.12/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.picocli)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "org.revengi.ApkDataMultiplex"
}