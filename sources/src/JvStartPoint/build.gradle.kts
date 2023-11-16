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
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("junit:junit:3.8.1")
}

application {
    mainClass.set("org.foomaa.jvchat.startpoint.JvStartPoint")
}

tasks.test {
    testLogging.showStandardStreams = true
    testClassesDirs("org.foomaa.jvchat.startpoint.AppTest")
    useJUnitPlatform()
    maxHeapSize = "1G"

    testLogging {
        events("passed", "failed", "skipped", "standardOut")
    }
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}