
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
            apply(libs.findLibrary("detekt-formatting").get().get().group.toString())
        }
    }
}