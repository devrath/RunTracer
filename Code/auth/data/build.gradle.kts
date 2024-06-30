plugins {
    alias(libs.plugins.runtracer.android.library)
    alias(libs.plugins.runtracer.jvm.ktor)
}

android {
    namespace = "com.istudio.auth.data"
}

dependencies {
    implementation(libs.bundles.koin)

    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}
