import com.google.protobuf.gradle.*

plugins {
    id("com.google.protobuf") version "0.8.18"
}

group = "org.foomaa.jvchat.messages"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("com.google.protobuf:protobuf-java:3.16.3")
    implementation(project(":JvLogger"))
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.16.3"
    }

    generatedFilesBaseDir = "${buildDir}/generated-sources/protobuf"

    generateProtoTasks {
        generatedFilesBaseDir = "${buildDir}/generated-sources/protobuf"
        all().forEach { task ->
            task.builtins {
                getByName("java")
            }
        }
    }
}

sourceSets {
    main {
        proto {
            srcDir("src/main/proto")
        }
    }
}

tasks {
    val deleteAllGeneratingFiles by creating(Delete::class) {
        val fileGenDir = file("${buildDir}/generated-sources")
        delete(fileGenDir.toString())
    }

    // может не надо
    val deleteGenPath by creating(Delete::class) {
        val fileGenDirMain = file("${buildDir}/generated")
        delete(fileGenDirMain.toString())

    }

    val deleteOldFiles by creating(Delete::class) {
        val pathGroup = project.group.toString().replace('.', '/')
        val protoDir = file("src/main/proto")
        var protoFiles = mutableListOf<String>()
        val endFileProto = ".proto"

        if (protoDir.exists() && protoDir.isDirectory) {
            val proto = protoDir.walk().filter { it.isFile }.map { it.name }.toList()
            protoFiles = proto.mapNotNull { fileName ->
                if (fileName.endsWith(endFileProto)) {
                    fileName.dropLast(endFileProto.length)
                } else {
                    null
                }
            }.toMutableList()
        }

        protoFiles.forEach{ fileName ->
            val fileProtoClass = file("src/main/java/${pathGroup}/${fileName}.java")
            if (fileProtoClass.exists() && fileProtoClass.isFile) {
                fileProtoClass.delete()
            }
        }
    }

    val copyProtobuf by creating(Copy::class) {
        from("${buildDir}/generated-sources/protobuf/main/java")
        into("src/main/java")
        dependsOn(deleteOldFiles, "generateProto")
        finalizedBy(deleteAllGeneratingFiles, deleteGenPath)
    }

    compileJava {
        finalizedBy(copyProtobuf)
    }
}

tasks {
    withType<Copy> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}