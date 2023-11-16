plugins {
    id("java")
}

group = "org.foomaa.jvchat.auth"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//    for new tests
//    testImplementation(platform("org.junit:junit-bom:5.9.1"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("junit:junit:3.8.1")
    implementation(project(":sources:lib:JvSystemSettings"))
    implementation(project(":sources:lib:JvUiComponents:JvAuthComponents"))
}

tasks.test {
    testLogging.showStandardStreams = true
    useJUnit()
//    for new tests
//    useTestNG()
//    useJUnitPlatform()
    maxHeapSize = "1G"
    failFast = true
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