rootProject.name = "JvChat"

include("sources:src:JvStartPoint")
include("sources:src:JvAuthentication")
include("sources:src:JvDbWorker")
include("sources:lib:JvSystemSettings")
include("sources:lib:JvUiComponents")
include("sources:lib:JvUiComponents:JvAuthComponents")
findProject("sources:lib:JvUiComponents:JvAuthComponents")?.name = "JvAuthComponents"