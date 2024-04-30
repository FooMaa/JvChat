group = "org.foomaa.jvchat.network"
version = "1.0-SNAPSHOT"
buildDir = File("jvchat-gradle")

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":sources:lib:JvSettings"))
    implementation("com.sun.mail:javax.mail:1.6.2")
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}