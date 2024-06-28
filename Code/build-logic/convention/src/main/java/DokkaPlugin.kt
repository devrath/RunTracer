
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.DokkaDefaults.suppress
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

class DokkaPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            target.run {
                pluginManager.apply("org.jetbrains.dokka")
            }
        }
    }
}
