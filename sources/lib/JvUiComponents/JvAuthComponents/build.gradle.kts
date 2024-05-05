group = "org.foomaa.jvchat.uicomponents.auth"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("org.springframework:spring-context:6.1.3")
    implementation("org.springframework:spring-beans:6.1.3")
    implementation("org.springframework:spring-core:6.1.6")

    implementation(project(":JvControls"))
    implementation(project(":JvSettings"))
    implementation(project(":JvMessages"))
    implementation(project(":JvTools"))
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
    resources.srcDir("../../../res/icons/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}