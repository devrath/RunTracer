# Android Application Convention Plugin
* Here we can include Android application-level configuration.

### `Elements added here`
* Application plugin --> `com.android.application`
* Kotlin Android --> `org.jetbrains.kotlin.android`
* `ApplicationId`
* `TargetSdk`
* `VersionCode`
* `VersionName`
* `Source & target compatibility`

### `Steps to add the plugin`

**`Kotlin.kt`**
```kotlin
import com.android.build.api.dsl.AndroidResources
import com.android.build.api.dsl.BuildFeatures
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.DefaultConfig
import com.android.build.api.dsl.Installation
import com.android.build.api.dsl.ProductFlavor
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun <BuildFeaturesT : BuildFeatures, BuildTypeT : BuildType, DefaultConfigT : DefaultConfig, ProductFlavorT : ProductFlavor, AndroidResourcesT : AndroidResources, InstallationT : Installation>
        Project.configureKotlinAndroid(
    commonExtension: CommonExtension<BuildFeaturesT, BuildTypeT, DefaultConfigT, ProductFlavorT, AndroidResourcesT, InstallationT>
) {
    commonExtension.apply {
        compileSdk = libs.findVersion("projectCompileSdkVersion").get().toString().toInt()

        defaultConfig.minSdk = libs.findVersion("projectMinSdkVersion").get().toString().toInt()

        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    configureKotlin()

    dependencies {
        "coreLibraryDesugaring"(libs.findLibrary("desugar.jdk.libs").get())
    }
}

private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }
}
```
**`ProjectExt.kt`**

This is to get access the the versionCatalog in android
```kotlin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")
```


**`AndroidApplicationConventionPlugin.kt`**
```kotlin
class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()

                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }

                configureKotlinAndroid(this)
            }
        }
    }

}
```

**build.gradle.kts**

Register the plugin in the `build-logic` gradle level
```kotlin
gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "runtracer.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}
```

# BuildType Configuration Plugin
**What we can replace**
```kotlin
buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
}
```


**Define buildtype file plugin**
```kotlin
internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *,*>,
    extensionType: ExtensionType,
    providers: ProviderFactory
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        val apiKey = gradleLocalProperties(rootDir,providers).getProperty("API_KEY")
        when(extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiKey)
                        }
                    }
                }
            }
            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiKey)
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType(apiKey: String) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"https://runique.pl-coding.com:8080\"")
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *,*>,
    apiKey: String
) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"https://runique.pl-coding.com:8080\"")

    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}
```

**Apply in ApplicationConventionPlugin**

```kotlin
class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            // Other code
            extensions.configure<ApplicationExtension> {
                // Other code

                // Add here -->
                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.APPLICATION,
                    providers = providers
                )
            }
        }
    }
}
```

# Android Application Compose Convention Plugin
## `Compose blocks to replace`
```kotlin
 buildFeatures {
    compose = true
 }
 composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
 }
```

## `Define the convention`
**AndroidCompose.kt**
```kotlin
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.run {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs
                .findVersion("composeCompiler")
                .get()
                .toString()
            //kotlinCompilerExtensionVersion = "1.5.1"
        }

        dependencies {
            val bom = libs.findLibrary("androidx.compose.bom").get()
            "implementation"(platform(bom))
            "androidTestImplementation"(platform(bom))
            "debugImplementation"(platform(bom))
            //"debugImplementation"(libs.findLibrary("androidx.compose.ui.tooling.preview").get())
        }
    }
}
```
## `Register the convention`
Add the code block in `build-logic` build gradle
```kotlin
// Other code

gradlePlugin {
    plugins {
        // other registrations of convention
        register("androidApplicationCompose") {
            id = "runtracer.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
    }
}
```

# Android Library Convention Plugin
* We can replace the library module to reflect as below
```kotlin
plugins {
    alias(libs.plugins.runtracer.android.library)
}

android {
    namespace = "com.istudio.run.network"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}
```

* Define `AndroidLibraryConventionPlugin`
```kotlin
class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.LIBRARY,
                    providers = providers
                )

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }

            dependencies {
                "testImplementation"(kotlin("test"))
            }
        }
    }
}
```
* Register in `build-logic` level build gradle
```kotlin
// Other Code

gradlePlugin {
    plugins {
        // Other Code
        register("androidLibrary") {
            id = "runtracer.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}
```

# Android Library Compose Convention Plugin
* Create the plugin below
* Also apply the library to get inherited `apply("runtracer.android.library")`
```kotlin 
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("runtracer.android.library")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}
```

# Android Feature Ui Convention Plugin
* Create a convention
```kotlin
fun DependencyHandlerScope.addUiLayerDependencies(project: Project) {
    "implementation"(project(":core:presentation:ui"))
    "implementation"(project(":core:presentation:designsystem"))

    "implementation"(project.libs.findBundle("koin.compose").get())
    "implementation"(project.libs.findBundle("compose").get())
    "debugImplementation"(project.libs.findBundle("compose.debug").get())
    "androidTestImplementation"(project.libs.findLibrary("androidx.compose.ui.test.junit4").get())
}
```
* apply it from a single place
```kotlin
class AndroidFeatureUiConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("runtracer.android.library.compose")
            }

            dependencies {
                addUiLayerDependencies(target)
            }
        }
    }
}
```
* Finally register it and apply in feature of presentation modules

# Android Room Convention Plugin
* Add the plugin below and register it
* We add this to the database module
```kotlin
class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("androidx.room")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                "implementation"(libs.findLibrary("room.runtime").get())
                "implementation"(libs.findLibrary("room.ktx").get())
                "ksp"(libs.findLibrary("room.compiler").get())
            }
        }
    }
}
```

# Jvm Library Convention Plugin
Add the JVM library convention plugin, register it and apply to all the domain layers

```kotlin
class JvmLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            configureKotlinJvm()
        }
    }
}
```