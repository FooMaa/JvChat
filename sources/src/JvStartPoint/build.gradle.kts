plugins {
    id("org.springframework.boot") version "3.2.4"
    application
}

group = "org.foomaa.jvchat.startpoint"
version = "1.0-SNAPSHOT"
extra["slf4j.version"] = "1.7.20"
var PROFILE = ""

dependencies {
    implementation(project(":JvControls"))
    implementation(project(":JvUiLinks"))
    implementation(project(":JvTools"))
    implementation(project(":JvSettings"))
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

tasks.withType<JavaExec>() {
    standardInput = System.`in`
}