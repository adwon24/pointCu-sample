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
            url = uri("https://nexus.adforus.com/repository/greenp/") // TODO GreenP 오퍼월 필요
        }
        maven {
            name = "Pangle"
            url = uri("https://artifact.bytedance.com/repository/pangle/") // TODO 애드팝콘 가이드에 명시 추가항목
        }
    }
}

rootProject.name = "samplePointCU"
include(":app")
 