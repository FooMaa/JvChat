plugins {
    id("java")
}

group = "org.foomaa.jvchat.uicomponents.auth"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:3.8.1")
    implementation(project(":sources:lib:JvSystemSettings"))
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

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
    resources.srcDir("../../../res/icons/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}