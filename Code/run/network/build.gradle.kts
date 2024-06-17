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