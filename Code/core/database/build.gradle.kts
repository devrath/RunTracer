plugins {
    alias(libs.plugins.runtracer.android.library)
    alias(libs.plugins.runtracer.android.room)
}

android {
    namespace = "com.istudio.core.database"
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.org.mongodb.bson)
}