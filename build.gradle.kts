plugins {
    id("java")
}

group = "org.foomaa.jvchat"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.run {
    group = "Execution"
    description = "Run the main class with JavaExecTask"
}

