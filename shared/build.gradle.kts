plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.ksp)
}

kotlin {
    jvm()

    androidLibrary {
        namespace = "com.drygin.popcornplan"
        compileSdk = 36
        minSdk = 26
    }

    val xcfName = "sharedKit"

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlin.stdlib)
                api(libs.kotlinx.coroutines.core)

                api(libs.uuid)
                api(libs.koin.core)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                // Retrofit
                implementation(libs.retrofit)
                implementation(libs.converter.moshi)

                // Moshi
                implementation(libs.moshi)
                implementation(libs.moshi.kotlin)

                // OkHttp with logs
                implementation(libs.okhttp)
                implementation(libs.logging.interceptor)

                // Room
                implementation(libs.room.ktx)
                implementation(libs.room.paging)
                implementation(libs.room.runtime)

                // Paging 3
                implementation(libs.paging.runtime)
                implementation(libs.paging.compose)

                // Koin
                implementation(libs.koin.core)
            }
        }

        iosMain {
            dependencies {
                
            }
        }
    }
}

dependencies {
    kspAndroid(libs.moshi.kotlin.codegen)
    kspAndroid(libs.room.compiler)
}