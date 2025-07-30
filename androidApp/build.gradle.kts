import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.composePlugin)
}

android.buildFeatures.buildConfig = true

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}
val traktApiKey: String = localProperties.getProperty("traktApiKey", "")

android {
    namespace = "com.drygin.popcornplan"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.drygin.popcornplan"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = "1.3.1.1"

        applicationVariants.all {
            outputs.all {
                val appName = "PopcornPlan"
                val versionName = versionName
                val buildType = buildType.name

                val outputFile = "$appName-v$versionName.apk"
                (this as? com.android.build.gradle.internal.api.BaseVariantOutputImpl)?.outputFileName = outputFile
            }
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "TRAKT_API_KEY", "\"${traktApiKey}\"")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas"
                )
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.compose.viewmodel)
    implementation(libs.icons.extended)
    implementation(platform(libs.androidx.compose.bom))

    // Coil
    implementation(libs.coil)

    // Koin
    implementation(libs.koin.androidx.navigation) // SavedStateHandle
    implementation(libs.koin.compose) // getViewModel() в Compose

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}