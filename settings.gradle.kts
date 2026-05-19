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

        maven {
            name = "OfferWall - AdForus GreenP v4"
            url = uri("https://nexus.adforus.com/repository/greenp/")
        }
        maven {
            name = "Pangle"
            url = uri("https://artifact.bytedance.com/repository/pangle/")
        }
    }
}

rootProject.name = "samplePointCU"
include(":app")
 