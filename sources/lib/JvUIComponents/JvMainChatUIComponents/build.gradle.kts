group = "org.foomaa.jvchat.uicomponents.auth"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":Controls"))
    implementation(project(":JvSettings"))
    implementation(project(":JvMessages"))
    implementation(project(":JvTools"))
    implementation(project(":JvLogger"))
    implementation(project(":GlobalDefines"))
    implementation(project(":JvStructObjects"))
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
    resources.srcDir("../../../res/JvMainChatUIComponents/icons/")
    resources.srcDir("../../../res/JvMainChatUIComponents/backgrounds/")
    resources.srcDir("../../../res/AllDefaultsRes/icons/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}