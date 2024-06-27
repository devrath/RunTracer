package com.istudio.convention

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named

internal fun Project.configureDetekt(extension: DetektExtension) =
    extension.apply {
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
            // "detektPlugins"(libs.findLibrary("twitter-compose").get())
        }
    }

inline fun Project.detektGradle(crossinline configure: DetektExtension.() -> Unit) =
    extensions.configure<DetektExtension> {
        configure()
    }
