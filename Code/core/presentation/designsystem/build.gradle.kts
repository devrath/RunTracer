plugins {
    alias(libs.plugins.runtracer.android.library.compose)
}

android {
    namespace = "com.istudio.core.presentation.designsystem"
}

dependencies {
    api(libs.androidx.material3)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.ui)
    debugImplementation(libs.androidx.ui.tooling)
}