group = "org.foomaa.jvchat.ctrl"
version = "1.0-SNAPSHOT"
buildDir = File("jvchat-gradle")

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-beans")
    implementation("org.springframework:spring-core")

    implementation(project(":sources:lib:JvSettings"))
    implementation(project(":sources:lib:JvMessages"))
    implementation(project(":sources:src:JvNetwork"))
    implementation(project(":sources:src:JvDbWorker"))
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}