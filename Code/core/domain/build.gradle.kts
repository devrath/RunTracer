plugins {
    alias(libs.plugins.runtracer.jvm.library)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}