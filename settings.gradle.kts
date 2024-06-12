import java.io.File

rootProject.name = "JvChat"

include("JvStartPoint")
project(":JvStartPoint").projectDir = File("sources/src/JvStartPoint")
include("JvUiLinks")
project(":JvUiLinks").projectDir = File("sources/src/JvUiLinks")
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
include("JvAuthUiComponents")
project(":JvAuthUiComponents").projectDir = File("sources/lib/JvUiComponents/JvAuthUiComponents")
// findProject("sources:lib:JvUiComponents:JvAuthUiComponents")?.name = "JvAuthUiComponents"