package com.istudio.convention

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