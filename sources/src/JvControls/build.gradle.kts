plugins {
    id("java")
}

group = "org.foomaa.jvchat.ctrl"
version = "1.0-SNAPSHOT"
buildDir = File("jvchat-gradle")

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:3.8.1")

    implementation("org.springframework:spring-context:6.1.3")
    implementation("org.springframework:spring-beans:6.1.3")
    implementation("org.springframework:spring-core:6.1.6")

    implementation(project(":sources:lib:JvSettings"))
    implementation(project(":sources:lib:JvMessages"))
    implementation(project(":sources:src:JvNetwork"))
    implementation(project(":sources:src:JvDbWorker"))
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