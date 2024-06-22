plugins {
    id("java")
}

group = "org.foomaa.jvchat"
version = "1.0-SNAPSHOT"

allprojects {
    buildDir = File("jvchat-gradle")
}

subprojects {
    apply(plugin = "java")

    dependencies {
        testImplementation("junit:junit:3.8.1")
        implementation("org.springframework:spring-context")
        implementation("org.springframework:spring-beans")
        implementation("org.springframework:spring-core")
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

// такая штука нужна, чтоб удалить принудительно папку сборки
//gradle.buildFinished() {
//    delete(project.buildDir)
//}

tasks.getByName<Jar>("jar") {
    enabled = false
}