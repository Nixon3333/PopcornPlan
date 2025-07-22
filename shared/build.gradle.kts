plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCompose)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget()
    iosSimulatorArm64()
    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material)
                implementation(libs.compose.ui)
                implementation(libs.compose.resources)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.lifecycle.runtime.ktx)
                implementation(libs.coil)
                implementation(libs.koin.android)
                implementation(libs.koin.androidx.navigation)
                implementation(libs.koin.compose)

                implementation(libs.retrofit)
                implementation(libs.converter.moshi)
                implementation(libs.okhttp)
                implementation(libs.logging.interceptor)

                implementation(libs.room.ktx)
                implementation(libs.room.runtime)
                implementation(libs.room.paging)

                implementation(libs.paging.runtime)
                implementation(libs.paging.compose)

                //implementation(libs.androidx.navigation)
                //implementation(libs.androidx.activity.compose)
                //implementation(platform(libs.androidx.compose.bom))
                //implementation(libs.androidx.compose.ui)
                //implementation(libs.androidx.compose.ui.graphics)
                //implementation(libs.androidx.compose.ui.tooling.preview)
                //implementation(libs.androidx.compose.material3)
                //implementation(libs.androidx.compose.viewmodel)
                //implementation(libs.icons.extended)
            }
        }

        val iosSimulatorArm64Main by getting {
            dependsOn(commonMain)
        }

        val desktopMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.compose.desktop.currentOs)
            }
        }
    }
}

android {
    namespace = "com.drygin.popcornplan.shared"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
}