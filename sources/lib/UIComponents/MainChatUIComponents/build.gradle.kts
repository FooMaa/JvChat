group = "org.foomaa.jvchat.uicomponents.auth"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":Controls"))
    implementation(project(":Settings"))
    implementation(project(":Messages"))
    implementation(project(":Tools"))
    implementation(project(":Logger"))
    implementation(project(":GlobalDefines"))
    implementation(project(":StructObjects"))
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
    resources.srcDir("../../../res/MainChatUIComponents/icons/")
    resources.srcDir("../../../res/MainChatUIComponents/backgrounds/")
    resources.srcDir("../../../res/AllDefaultsRes/icons/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}