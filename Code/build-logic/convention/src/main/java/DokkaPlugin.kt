
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.DokkaDefaults.suppress
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTask

class DokkaPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            target.run {
                pluginManager.apply("org.jetbrains.dokka")
            }


        }
        target.tasks.withType(DokkaTask::class.java).configureEach {
            dokkaSourceSets.configureEach {
                suppress.set(true)
            }
        }
    }
}
