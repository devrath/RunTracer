
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
                config.from(files("config/datekt/detekt.yml")) // Or use config.setFrom(files(...))

            }
        }
    }

    private fun Project.applyPlugins() {
        pluginManager.apply {
            apply(libs.findLibrary("detekt-gradlePlugin").get().get().group.toString())
        }
    }
}