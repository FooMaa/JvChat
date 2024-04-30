group = "org.foomaa.jvchat.auth"
version = "1.0-SNAPSHOT"
buildDir = File("jvchat-gradle")

dependencies {
    implementation(project(":sources:lib:JvUiComponents:JvAuthComponents"))
}

tasks {
    javadoc {
        options.encoding = "UTF-8"
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    compileTestJava {
        options.encoding = "UTF-8"
    }
}