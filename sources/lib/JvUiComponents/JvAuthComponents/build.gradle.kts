plugins {
    id("java")
}

group = "org.foomaa.jvchat.uicomponents.auth"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//    implementation(project(mapOf("path" to ":sources:lib:JvSystemSettings")))
//    for new tests
//    testImplementation(platform("org.junit:junit-bom:5.9.1"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("junit:junit:3.8.1")
    implementation("net.imagej:ij:1.51h")
    implementation(project(":sources:lib:JvSystemSettings"))
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
allprojects {
    tasks {
        javadoc {
            options.encoding = "UTF-8"
        }
        compileJava {
            options.encoding = "UTF-8"
        }
        compileTestJava {
            options.encoding = "UTF-8"
        }
    }
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
    resources.srcDir("src/main/resources/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}