import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java")
    jacoco
    application
}

application {
    mainClass.set("hexlet.code.App")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    // Javalin
    implementation("io.javalin:javalin:6.6.0")
    implementation("io.javalin:javalin-rendering:6.6.0")

    // Логи
    implementation("org.slf4j:slf4j-simple:2.0.7")

    // База данных
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.h2database:h2:2.3.232")
    implementation("org.postgresql:postgresql:42.7.5")

    // JTE
    implementation("gg.jte:jte:3.1.9")

    // Тесты
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        exceptionFormat = TestExceptionFormat.FULL

        events = mutableSetOf(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED
        )

        showStandardStreams = true
    }
}