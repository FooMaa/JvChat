group = "org.foomaa.jvchat.uicomponents.auth"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":JvControls"))
    implementation(project(":JvSettings"))
    implementation(project(":JvMessages"))
    implementation(project(":JvTools"))
    implementation(project(":JvLogger"))
    implementation(project(":JvMainChatUIComponents"))
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
    resources.srcDir("../../../res/JvAuthUIComponents/icons/")
    resources.srcDir("../../../res/AllDefaultsRes/icons/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}