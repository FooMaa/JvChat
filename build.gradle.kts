plugins {
    id("java")
}

subprojects {
    apply(plugin = "java")

    dependencies {
        testImplementation("junit:junit:3.8.1")
        implementation("org.springframework.boot:spring-boot-starter:3.2.4")
    }

    repositories {
        mavenCentral()
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
}

group = "org.foomaa.jvchat"
version = "1.0-SNAPSHOT"
buildDir = File("jvchat-gradle")

// такая штука нужна, чтоб удалить принудительно папку сборки
//gradle.buildFinished() {
//    delete(project.buildDir)
//}

tasks.jar {
    enabled = false
}
