import java.io.ByteArrayOutputStream

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

val gitCommitCount = 100 + "git rev-list --count HEAD".runCommand().toInt()
val gitCommitId = "git rev-parse --short HEAD".runCommand()

android {
    namespace = "de.mchllngr.devsettings"
    compileSdk = 34

    defaultConfig {
        applicationId = "de.mchllngr.devsettings"
        minSdk = 28
        targetSdk = 34
        versionCode = gitCommitCount
        versionName = "2.0.0-$gitCommitId"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.kotlin.android.coroutines)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
}

fun String.runCommand(currentWorkingDir: File = file("./")): String {
    val byteArray = ByteArrayOutputStream().use { byteOut ->
        project.exec {
            workingDir = currentWorkingDir
            commandLine = this@runCommand.split("\\s".toRegex())
            standardOutput = byteOut
        }
        return@use byteOut.toByteArray()
    }
    return String(byteArray).trim()
}
