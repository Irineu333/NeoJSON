import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose)
}

kotlin {

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    jvm("desktop")

    sourceSets {

        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.activity)
            implementation(libs.androidx.activity.compose)
        }

        commonMain.dependencies {

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.materialIconsExtended)

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.voyager.screenModel)
            implementation(libs.voyager.navigator)
        }

        desktopMain.dependencies {

            implementation(libs.kotlinx.coroutines.swing)

            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "com.neoutils.json"
    compileSdk = 34

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.neoutils.json"

        minSdk = 24
        targetSdk = 34

        versionCode = 1
        versionName = "1.0.0"
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10" // Compatible with Kotlin 1.9.22
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    dependencies {
        debugImplementation(libs.androidx.compose.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "com.neoutils.json.ui.MainKt"

        nativeDistributions {

            targetFormats(TargetFormat.Exe, TargetFormat.Rpm)

            packageName = "com.neoutils.json"
            packageVersion = "1.0.0"
        }
    }
}