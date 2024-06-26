import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class DetektPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val projectSource = project.file(project.projectDir)
        val detektConfigFile = project.files("${project.rootDir}/config/detekt/detekt.yml")
        val detektBaselineFile = project.file("${project.rootDir}/config/detekt/detekt-baseline.xml")
        val kotlinFiles = "**/*.kt"
        val detektExclude = listOf(
            "**/resources/**",
            "**/build/**",
            "**/test/**",
            "**/androidTest/**"
        )

        project.tasks.register("detektAll", Detekt::class) {
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

        project.tasks.register("detektGenerateBaseline", DetektCreateBaselineTask::class) {
            description = "Overrides current Detekt baseline."
            buildUponDefaultConfig.set(true)
            ignoreFailures.set(false)
            parallel.set(true)
            setSource(project.files(project.rootDir))
            config.setFrom(project.files(detektConfigFile))
            baseline.set(detektBaselineFile)
            include("**/*.kt")
            include("**/*.kts")
            exclude("**/resources/**")
            exclude("**/build/**")
        }
    }
}