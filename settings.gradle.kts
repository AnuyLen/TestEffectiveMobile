pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Test EffectiveMobile"
include(":app")
include(":feature:login")
include(":core:brandbook")
include(":domain")
include(":feature:main")
include(":data")
include(":core:common")
include(":feature:account")
include(":feature:favorites")
include(":feature:course_page")
include(":core:adapters")
