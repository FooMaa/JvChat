group = "org.foomaa.jvchat.messages"
version = "1.0-SNAPSHOT"

plugins {
    id("com.google.protobuf") version "0.8.18"
}

dependencies {
    implementation ("com.google.protobuf:protobuf-java:3.16.3")
    implementation(project(":JvLogger"))
}

//protobuf {
//    protoc {
//        artifact = "com.google.protobuf:protoc:3.16.3"
//    }
//}