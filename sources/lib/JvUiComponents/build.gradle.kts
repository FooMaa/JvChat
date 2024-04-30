group = "org.foomaa.jvchat.uicomponents"
version = "1.0-SNAPSHOT"
buildDir = File("jvchat-gradle")

repositories {
    mavenCentral()
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}