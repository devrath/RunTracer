plugins {
    alias(libs.plugins.runtracer.android.library)
    alias(libs.plugins.runtracer.jvm.ktor)
}

android {
    namespace = "com.istudio.core.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(libs.timber)
    implementation(libs.bundles.koin)
    implementation(project(":auth:domain"))
}