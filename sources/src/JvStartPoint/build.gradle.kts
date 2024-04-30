plugins {
    id("java")
    id("org.springframework.boot") version "3.2.4"
    application
}

group = "org.foomaa.jvchat.startpoint"
version = "1.0-SNAPSHOT"
buildDir = File("jvchat-gradle")
extra["slf4j.version"] = "1.7.20"
var PROFILE = ""

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:3.8.1")

    implementation("org.springframework.boot:spring-boot-starter:3.2.4")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-beans")
    implementation("org.springframework:spring-core")

    implementation(project(":sources:src:JvControls"))
    implementation(project(":sources:src:JvAuthentication"))
    implementation(project(":sources:lib:JvTools"))
    implementation(project(":sources:lib:JvSettings"))
}

tasks {
    "run" {
        onlyIf {
            project.hasProperty("users") || project.hasProperty("servers")
        }
    }

    "build" {
        doLast {
            var count = 0
            if (project.hasProperty("tests")) {
                count++
                PROFILE = "tests"
            }
            if (project.hasProperty("users")) {
                count++
                PROFILE = "users"
            }
            if (project.hasProperty("servers")) {
                count++
                PROFILE = "servers"
            }
            if (count == 0) {
                throw GradleException("No profile!")
            }
            if (count > 1) {
                throw GradleException("Wrong profile!")
            }
            var dirBuild = project.buildDir.toString()
            delete("$dirBuild/profile")
            project.file("$dirBuild/profile").mkdir()
            project.file("$dirBuild/profile/profile.txt").createNewFile()
            project.file("$dirBuild/profile/profile.txt").writeText("#Properties\ntarget=$PROFILE")

            bootRun {
                mainClass.set("org.foomaa.jvchat.startpoint.JvStartPoint")
                args("--spring.profiles.active=$PROFILE")
                systemProperty("spring.profiles.active", PROFILE)
                systemProperty("java.awt.headless", "false")
            }

            ext{PROFILE}
        }
    }
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

tasks.withType<JavaExec>() {
    standardInput = System.`in`
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}