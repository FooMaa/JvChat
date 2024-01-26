plugins {
    id("java")
    application
}

group = "org.foomaa.jvchat.startpoint"
version = "1.0-SNAPSHOT"
buildDir = File("jvchat-gradle")
var PROFILE = ""

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":sources:src:JvControls"))
    implementation(project(":sources:src:JvAuthentication"))
    testImplementation("junit:junit:4.13.2")
}

application {
    mainClass.set("org.foomaa.jvchat.startpoint.JvStartPoint")
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

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}