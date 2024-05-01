import java.io.File

rootProject.name = "JvChat"

include("JvStartPoint")
project(":JvStartPoint").projectDir = File("sources/src/JvStartPoint")
include("JvAuthentication")
project(":JvAuthentication").projectDir = File("sources/src/JvAuthentication")
include("JvDbWorker")
project(":JvDbWorker").projectDir = File("sources/src/JvDbWorker")
include("JvControls")
project(":JvControls").projectDir = File("sources/src/JvControls")
include("JvNetwork")
project(":JvNetwork").projectDir = File("sources/src/JvNetwork")
include("JvSettings")
project(":JvSettings").projectDir = File("sources/lib/JvSettings")
include("JvTools")
project(":JvTools").projectDir = File("sources/lib/JvTools")
include("JvMessages")
project(":JvMessages").projectDir = File("sources/lib/JvMessages")
include("JvUiComponents")
project(":JvUiComponents").projectDir = File("sources/lib/JvUiComponents")
include("JvAuthComponents")
project(":JvAuthComponents").projectDir = File("sources/lib/JvUiComponents/JvAuthComponents")
// findProject("sources:lib:JvUiComponents:JvAuthComponents")?.name = "JvAuthComponents"