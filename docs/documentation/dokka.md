## `Why build logic module is needed`
* We need to have a common place to store the shared config in our app like `compile-sdk`, `build-types`, `compile-options`, `etc`...
* This is useful so that we do not need to do this for every single module in the application.

## `How to achieve this`
* We can do this in a way called `included build`.
* This `included module` is meant to `contain` the `gradle specific configuration`.
* This module is also meant to be `consumed` by other `gradle-specific` files.
* Here we will just put the configuration that is needed at compile time.
* `included build` is just adding another gradle project to our project. So we can imagine an entire project with `app-modules`, and `individual-subModules` all together become one module, and `included build` becomes a separate project

## `How to differentiate between normal kotlin module and gradle specific module`
* Say you have created a `build-logic` module in the application, Your `settings.gradle.kts` will look like this.
```gradle
pluginManagement {
    includeBuild("build-logic") //---------------------------------> Add this line
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
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "YourApp"
include(":app")
include(":auth:data")
```
## `Difference between BuildSrc and BuildLogic`
* `BuildSrc` --> Here say there are multiple plugins present, when one of them is applied all re-build
* `BuildLogic` --> Here say there are multiple plugins present, when one of them is applied only that plugin is re-built
