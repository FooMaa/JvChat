plugins {
    id("java")
    application
}

group = "org.foomaa.jvchat.startpoint"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":sources:src:JvDbWorker"))
    implementation(project(":sources:src:JvAuthentication"))
    testImplementation("junit:junit:3.8.1")

}

application {
    mainClass.set("org.foomaa.jvchat.startpoint.JvStartPoint")
}

tasks.test {
    testLogging.showStandardStreams = true

    useJUnit()

    maxHeapSize = "1G"
    failFast = true

    testLogging {
        events("passed", "failed", "skipped", "standardOut")
    }
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}