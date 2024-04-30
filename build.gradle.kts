plugins {
    id("java")
    id("org.springframework.boot") version "3.2.4" apply false
}

subprojects {
    apply(plugin = "java")
    dependencies {
        testImplementation("junit:junit:3.8.1")
        implementation("org.springframework.boot:spring-boot-starter:3.2.4")
    }
}

group = "org.foomaa.jvchat"
version = "1.0-SNAPSHOT"
buildDir = File("jvchat-gradle")

// такая штука нужна, чтоб удалить принутительно папку сборки
//gradle.buildFinished() {
//    delete(project.buildDir)
//}

tasks.jar {
    enabled = false
}