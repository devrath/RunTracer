plugins {
    alias(libs.plugins.runtracer.android.feature.ui)
}

android {
    namespace = "com.istudio.auth.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}