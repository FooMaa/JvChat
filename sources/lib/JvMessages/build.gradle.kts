import com.google.protobuf.gradle.*

plugins {
    id("com.google.protobuf") version "0.9.4"
}

group = "org.foomaa.jvchat.messages"
version = "1.0-SNAPSHOT"
var protoPath = ""

dependencies {
    implementation("com.google.protobuf:protobuf-java:3.16.3")
    implementation("io.grpc:grpc-stub:1.15.1")
    implementation("io.grpc:grpc-protobuf:1.15.1")
    implementation(project(":JvLogger"))
    implementation(project(":JvGlobalDefines"))
    implementation(project(":JvTools"))
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.16.3"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.15.1"
        }
    }

    //generatedFilesBaseDir = "${buildDir}/generated-sources/protobuf"
    protoPath = generatedFilesBaseDir
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc") {}
            }
        }
    }
}


tasks {
    val deleteGeneratingPath by creating(Delete::class) {
        val pattern = "generated/"
        val index = protoPath.indexOf(pattern)
        val subPath: String
        if (index != -1) {
            subPath = protoPath.substring(0, index + pattern.length)
            val fileGeneratingMainDir = file(subPath)
            delete(fileGeneratingMainDir.toString())
        }
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
        from("${protoPath}/main/java")
        into("src/main/java")
        dependsOn(deleteOldFiles, "generateProto")
        finalizedBy(deleteGeneratingPath)
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