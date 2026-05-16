import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("io.freefair.lombok") version "8.13"
    application
    jacoco
    checkstyle
    id("org.sonarqube") version "7.3.0.8198"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("hexlet.code.App")
}

dependencies {

    implementation("io.javalin:javalin:6.5.0")

    implementation("io.javalin:javalin-bundle:6.5.0")

    implementation("org.slf4j:slf4j-simple:2.0.17")

    implementation("gg.jte:jte:3.1.16")

    implementation("io.javalin:javalin-rendering:6.5.0")

    implementation("com.h2database:h2:2.2.220")

    implementation("com.zaxxer:HikariCP:6.2.1")

    testImplementation("org.assertj:assertj-core:3.27.3")

    testImplementation(platform("org.junit:junit-bom:5.10.1"))

    testImplementation("org.junit.jupiter:junit-jupiter")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("com.konghq:unirest-java:4.0.0-RC2")

    implementation("org.jsoup:jsoup:1.19.1")

    implementation("org.postgresql:postgresql:42.7.5")

    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        showStandardStreams = true
    }
}

sonar {
    properties {
        property("sonar.projectKey", "NikitaOguz_java-project-72")
        property("sonar.organization", "nikitoguzkov")
    }
}
