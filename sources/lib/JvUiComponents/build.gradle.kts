plugins {
    id("java")
}

group = "org.foomaa.jvchat.uicomponents"
version = "1.0-SNAPSHOT"
buildDir = File("jvchat-gradle")

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:3.8.1")
    implementation(project(":sources:lib:JvSettings"))
}


tasks.test {
    onlyIf {
        project.hasProperty("tests")
    }

    useJUnit()

    maxHeapSize = "1G"
    failFast = true

    testLogging {
        events("passed", "failed", "skipped")
    }
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}