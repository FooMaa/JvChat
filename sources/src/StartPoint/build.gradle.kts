plugins {
    id("org.springframework.boot") version "4.0.5"
    application
}

group = "org.foomaa.jvchat.startpoint"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":Controls"))
    implementation(project(":UILinks"))
    implementation(project(":Tools"))
    implementation(project(":Settings"))
    implementation(project(":Logger"))
}

application {
    mainClass.set("org.foomaa.jvchat.startpoint.MainStartPoint")
}

val knownProfiles = listOf("users", "servers", "tests")

val activeProfile: String? = knownProfiles.firstOrNull {
    project.hasProperty(it)
}

val effectiveProfile = activeProfile ?: "users"

fun requireProfile() {
    if (activeProfile == null) {
        throw GradleException("No profile! Error!")
    }
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    doFirst { requireProfile() }
    mainClass.set("org.foomaa.jvchat.startpoint.MainStartPoint")
    onlyIf { activeProfile != "tests" }

    standardInput = System.`in`
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
        attributes["Profile"] = effectiveProfile
    }
}
