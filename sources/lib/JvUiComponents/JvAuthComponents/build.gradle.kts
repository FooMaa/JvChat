group = "org.foomaa.jvchat.uicomponents.auth"
version = "1.0-SNAPSHOT"

dependencies {
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