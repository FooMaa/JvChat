group = "org.foomaa.jvchat.messages"
version = "1.0-SNAPSHOT"
buildDir = File("jvchat-gradle")

repositories {
    mavenCentral()
}

dependencies {
    implementation ("com.google.protobuf:protobuf-java:3.16.3")
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}