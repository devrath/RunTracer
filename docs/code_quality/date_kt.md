![png](https://github.com/devrath/RunTracer/assets/1456191/8245a9b4-9814-4e87-938f-7c09a3913801)
## `About DateKt`
* Datekt is a static code analysis tool for kotlin that is capable of identifying
   * Code smells
   * Un-necessary complexity
   * Large class files
   * Poor coding practice
   * And other things that affect code quality

## `Project location`
[_`Documentation`_](https://detekt.dev/)

## `Steps to add the Gradle`
* Add to project-level build.gradle
```kotlin
detekt = { id = "io.gitlab.arturbosch.detekt", version.ref = "detekt" }
```
* Module level gradle script is added using the script below
```kotlin
detekt-formatting = { group = "io.gitlab.arturbosch.detekt", name = "detekt-formatting", version.ref = "detekt" }
```

## `How to run the plugin`
* In the terminal run the below command
```kotlin
./gradlew detekt
```

## `Useful projects for reference`
* [`Demo-App-Compose-News`](https://github.com/Kaaveh/ComposeNews)

## `Steps to add plugin for for multi-module`
* **Step-1** `Define the convention` -> `Datekt.kt`
```kotlin
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named

internal fun Project.configureDetekt(extension: DetektExtension) = extension.apply {

    val projectSource = project.file(project.projectDir)
    val detektConfigFile = project.files("${project.rootDir}/config/detekt/detekt.yml")
    val detektBaselineFile = project.file("${project.rootDir}/config/detekt/detekt-baseline.xml")
    val kotlinFiles = "**/*.kt"
    val detektExclude = listOf("**/resources/**", "**/build/**", "**/test/**", "**/androidTest/**")

    tasks.named<Detekt>("detekt") {
        val autoFix = project.hasProperty("detektAutoFix")
        val noBaseline = project.hasProperty("noBaseline")

        description = "Runs detekt for all modules."
        parallel = true
        ignoreFailures = false
        autoCorrect = autoFix
        buildUponDefaultConfig = true
        setSource(projectSource)
        if (!noBaseline) {
            baseline.set(detektBaselineFile)
        }
        config.setFrom(detektConfigFile)
        include(kotlinFiles)
        exclude(detektExclude)

        reports {
            xml.required.set(true)
            html.required.set(true)
            txt.required.set(true)
            sarif.required.set(true)
            md.required.set(true)
        }
    }
    dependencies {
        "detektPlugins"(libs.findLibrary("detekt-formatting").get())
    }
}

inline fun Project.detektGradle(crossinline configure: DetektExtension.() -> Unit) =
    extensions.configure<DetektExtension> {
        configure()
    }
```

* **Step-2** `Define the plugin that uses the convention` -> `DetektPlugin.kt`
```kotlin
import com.istudio.convention.configureDetekt
import com.istudio.convention.detektGradle
import com.istudio.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class DetektPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        project.run {
            applyPlugins()
            detektGradle {
                configureDetekt(this)
            }
        }
    }

    private fun Project.applyPlugins() {
        pluginManager.apply {
            apply(libs.findLibrary("detekt-gradlePlugin").get().get().group.toString())
        }
    }
}
```
* **Step-3** Register the plugin in the convention gradle file same as for other plugins.
* **Step-4** Apply the plugin in the modules where it is needed `apply("runtracer.quality.dateKt")`


## `Plugins that can be added`
* __DateKt plugin for formatting__
```kotlin
detekt-formatting = { group = "io.gitlab.arturbosch.detekt", name = "detekt-formatting", version.ref = "detekt" }
```
* [__Twitter plugin for composables__](https://twitter.github.io/compose-rules/detekt/)
```kotlin
detekt-twitter-compose = { module = "com.twitter.compose.rules:detekt", version.ref = "detekt-twitter-compose" }
``` 