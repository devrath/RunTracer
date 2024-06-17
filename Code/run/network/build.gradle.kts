plugins {
    alias(libs.plugins.runtracer.android.library)
    alias(libs.plugins.runtracer.jvm.ktor)
}

android {
    namespace = "com.istudio.run.network"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}