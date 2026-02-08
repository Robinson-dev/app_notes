pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()          // Repositorio de Google (Android, Compose, etc.)
        mavenCentral()    // Repositorio central de Maven (mayoría de librerías)
    }
}

rootProject.name = "Notes"  // Nombre del proyecto
include(":app")             // Módulos incluidos