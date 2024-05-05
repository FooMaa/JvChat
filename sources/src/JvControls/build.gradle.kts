group = "org.foomaa.jvchat.ctrl"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-beans")
    implementation("org.springframework:spring-core")

    implementation(project(":JvSettings"))
    implementation(project(":JvMessages"))
    implementation(project(":JvNetwork"))
    implementation(project(":JvDbWorker"))
}