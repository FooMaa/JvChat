plugins {
    id("java")
    application
}

group = "org.foomaa.jvchat.startpoint"
version = "1.0-SNAPSHOT"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("junit:junit:3.8.1")
}

application {
    mainClass.set("org.foomaa.jvchat.startpoint.JvStartPoint")
}

tasks.test {
    useJUnitPlatform()
    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}
sourceSets {
    test {
            java.srcDir("src/test")
    }
}