import org.gradle.internal.classpath.Instrumented.systemProperty

plugins {
    id("org.springframework.boot") version "3.2.4"
    application
}

group = "org.foomaa.jvchat.startpoint"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":Controls"))
    implementation(project(":UILinks"))
    implementation(project(":Tools"))
    implementation(project(":Settings"))
}

application {
    mainClass.set("org.foomaa.jvchat.startpoint.MainStartPoint")
}

val activeProfile: String? = listOf("users", "servers", "tests")
    .firstOrNull { project.hasProperty(it) }

if (activeProfile == null) {
    throw GradleException("No profile!")
}

tasks {
    named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
        mainClass.set("org.foomaa.jvchat.startpoint.MainStartPoint")
        args("--spring.profiles.active=$activeProfile")
        systemProperty("spring.profiles.active", activeProfile)
        systemProperty("java.awt.headless", "false")

        onlyIf { activeProfile != "tests" }
    }

    test {
        onlyIf { activeProfile == "tests" }
    }
}
