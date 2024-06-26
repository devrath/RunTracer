import com.android.build.api.dsl.LibraryExtension
import com.istudio.convention.configureBuildTypes
import com.istudio.convention.configureKotlinAndroid
import com.istudio.convention.ExtensionType
import com.istudio.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("runtracer.android.library")
                apply("io.gitlab.arturbosch.detekt")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}