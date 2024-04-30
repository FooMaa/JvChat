rootProject.name = "JvChat"

include("sources:src:JvStartPoint")
include("sources:src:JvAuthentication")
include("sources:src:JvDbWorker")
include("sources:src:JvControls")
include("sources:src:JvNetwork")
include("sources:lib:JvSettings")
include("sources:lib:JvTools")
include("sources:lib:JvMessages")
include("sources:lib:JvUiComponents")
include("sources:lib:JvUiComponents:JvAuthComponents")
// findProject("sources:lib:JvUiComponents:JvAuthComponents")?.name = "JvAuthComponents"