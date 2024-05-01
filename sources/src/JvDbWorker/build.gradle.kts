group = "org.foomaa.jvchat.dbworker"
version = "1.0-SNAPSHOT"
buildDir = File("jvchat-gradle")

dependencies {
    testImplementation("junit:junit:3.8.1")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-beans")
    implementation("org.springframework:spring-core")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation(project(":JvSettings"))
}