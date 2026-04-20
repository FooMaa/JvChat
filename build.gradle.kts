plugins {
    id("java")
    id("com.diffplug.spotless") version "6.25.0" apply false
}

group = "org.foomaa.jvchat"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
    buildDir = File("jvchat-gradle")

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    tasks.withType<Javadoc> {
        options.encoding = "UTF-8"
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "com.diffplug.spotless")

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        java {
            palantirJavaFormat()
            removeUnusedImports()
            importOrder(
                "java",
                "javax",
                "org",
                "com",
                "lombok",
                "org.foomaa.jvchat",
                "")
            endWithNewline()
            trimTrailingWhitespace()
            targetExclude("**/*_pb.java")
        }

        isEnforceCheck = false
    }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter:5.14.3")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.14.3")
        testImplementation("org.assertj:assertj-core:3.27.7")
        testImplementation("org.mockito:mockito-core:5.23.0")
        testImplementation("org.mockito:mockito-junit-jupiter:5.23.0")
        implementation("org.springframework:spring-context")
        implementation("org.springframework:spring-beans")
        implementation("org.springframework:spring-core")
        implementation("org.springframework.boot:spring-boot-starter:3.2.4")

        compileOnly("org.projectlombok:lombok:1.18.30")
        annotationProcessor("org.projectlombok:lombok:1.18.30")
        testCompileOnly("org.projectlombok:lombok:1.18.30")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
    }

    tasks.test {
        onlyIf {
            project.hasProperty("tests")
        }
        useJUnitPlatform()
        maxHeapSize = "1G"
        failFast = true
        testLogging {
            events("passed", "failed", "skipped")
        }
    }
}

// This thing is needed to remove the power supply of the assembly
//gradle.buildFinished() {
//    delete(project.buildDir)
//}

tasks.getByName<Jar>("jar") {
    enabled = false
}