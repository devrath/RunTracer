plugins {
    alias(libs.plugins.runtracer.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
}