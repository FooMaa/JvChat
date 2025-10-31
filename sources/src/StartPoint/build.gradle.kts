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
    throw GradleException("No profile! Use -Pusers, -Pservers, or -Ptests")
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    mainClass.set("org.foomaa.jvchat.startpoint.MainStartPoint")
    onlyIf { activeProfile != "tests" }
}


tasks.test {
    onlyIf { activeProfile == "tests" }
}

val generatedResourcesDir = layout.buildDirectory.dir("generated-resources")
val generateProfile = tasks.register("generateProfile") {
    val outFile = generatedResourcesDir.map { it.file("profile.properties") }
    outputs.file(outFile)

    doLast {
        outFile.get().asFile.parentFile.mkdirs()
        outFile.get().asFile.writeText("#AUTO-GENERATED\nProfile=$activeProfile")
    }
}

tasks.processResources {
    dependsOn(generateProfile)
    from(generatedResourcesDir)
}

tasks.withType<Jar>().configureEach {
    manifest {
        attributes["Profile"] = activeProfile
    }
}
