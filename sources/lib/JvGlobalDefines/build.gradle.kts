group = "org.foomaa.jvchat.globaldefines"
version = "1.0-SNAPSHOT"

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

sourceSets.getByName("main") {
    resources.srcDir("../../../res/AllDefaultsRes/fonts/")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/java/")
}