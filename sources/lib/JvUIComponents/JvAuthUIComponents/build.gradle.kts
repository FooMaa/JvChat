group = "org.foomaa.jvchat.uicomponents.auth"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":JvControls"))
    implementation(project(":JvSettings"))
    implementation(project(":JvMessages"))
    implementation(project(":JvTools"))
    implementation(project(":JvLogger"))
    implementation(project(":JvEvents"))
    implementation(project(":JvGlobalDefines"))
    implementation(project(":JvMainChatUIComponents"))
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
    resources.srcDir("../../../res/JvAuthUIComponents/icons/")
    resources.srcDir("../../../res/JvAuthUIComponents/backgrounds/")
    resources.srcDir("../../../res/AllDefaultsRes/icons/")
    resources.srcDir("../../../res/AllDefaultsRes/gifs/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}