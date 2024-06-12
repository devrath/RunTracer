plugins {
    `kotlin-dsl`
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

group = "com.istudio.runtracer.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}