group = "org.foomaa.jvchat.network"
version = "1.0-SNAPSHOT"
buildDir = File("jvchat-gradle")

dependencies {
    implementation(project(":JvSettings"))
    implementation("com.sun.mail:javax.mail:1.6.2")
}