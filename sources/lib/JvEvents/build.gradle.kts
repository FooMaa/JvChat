group = "org.foomaa.jvchat.events"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("net.bytebuddy:byte-buddy:1.16.1")
    implementation("org.aspectj:aspectjweaver:1.9.22.1")
    implementation(project(":JvModels"))
    implementation(project(":JvStructObjects"))
}
