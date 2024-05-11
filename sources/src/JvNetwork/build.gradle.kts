group = "org.foomaa.jvchat.network"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("com.sun.mail:javax.mail:1.6.2")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-beans")
    implementation("org.springframework:spring-core")
    implementation(project(":JvSettings"))
}