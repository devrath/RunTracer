import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.androidDynamicFeature) apply false
    alias(libs.plugins.mapsplatform.secrets.plugin) apply false
    alias(libs.plugins.detekt) apply false
    //alias(libs.plugins.runtracer.quality.datekt) apply false
}


//** ************************************* DATE-KT *************************************************
val projectSource = file(projectDir)
val detektConfigFile = files("$rootDir/config/detekt/detekt.yml")
val detektBaselineFile = file("$rootDir/config/detekt/detekt-baseline.xml")
val kotlinFiles = "**/*.kt"
val detektExclude = listOf(
    "**/resources/**",
    "**/build/**",
    "**/test/**",
    "**/androidTest/**"
)

tasks {
    register("detektAll", Detekt::class) {
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
            html.required.set(true)
            xml.required.set(true)
            txt.required.set(true)
            md.required.set(true)
            sarif.required.set(false)
        }
    }

    register("detektGenerateBaseline", DetektCreateBaselineTask::class) {
        description = "Overrides current Detekt baseline."
        buildUponDefaultConfig.set(true)
        ignoreFailures.set(false)
        parallel.set(true)
        setSource(files(rootDir))
        config.setFrom(files(detektConfigFile))
        baseline.set(file(detektBaselineFile))
        include("**/*.kt")
        include("**/*.kts")
        exclude("**/resources/**")
        exclude("**/build/**")
    }
}
//** ************************************* DATE-KT *************************************************