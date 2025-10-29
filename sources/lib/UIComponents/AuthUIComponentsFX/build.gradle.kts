plugins {
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "org.jvchat.uicomponents.authfx"
version = "1.0-SNAPSHOT"

javafx {
    version = "22"
    modules = listOf(
        "javafx.controls",
        "javafx.fxml",
        "javafx.graphics"
    )
}

dependencies {

}

sourceSets.getByName("main") {
    java.srcDir("src/main/java/")
    resources.srcDir("../../../res/AuthUIComponents/icons/")
    resources.srcDir("../../../res/AuthUIComponents/backgrounds/")
    resources.srcDir("../../../res/AllDefaultsRes/icons/")
    resources.srcDir("../../../res/AllDefaultsRes/gifs/")
}

sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}