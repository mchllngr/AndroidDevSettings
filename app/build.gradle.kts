import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

val gitCommitCount = 100 + "git rev-list --count HEAD".runCommand().toInt()
val gitCommitId = "git rev-parse --short HEAD".runCommand()
val gitLatestVersionTag = "git describe --tags --match=v[0-9].[0-9].[0-9] HEAD".runCommand()
val appVersionName = Regex("([0-9].[0-9].[0-9])").find(gitLatestVersionTag)?.value ?: error("no version found in tag '$gitLatestVersionTag'")

val keystoreProperties = Properties().apply {
    runCatching {
        load(FileInputStream(File(rootProject.rootDir, "keystore.properties")))
    }
}

android {
    namespace = "de.mchllngr.devsettings"
    compileSdk = 35

    defaultConfig {
        applicationId = "de.mchllngr.devsettings"
        minSdk = 28
        targetSdk = 35
        versionCode = gitCommitCount
        versionName = "$appVersionName-$gitCommitId"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file(keystoreProperties.getProperty("storeFile") ?: "KEYSTORE_PROPERTIES_NOT_PROVIDED")
            storePassword = keystoreProperties.getProperty("storePassword")
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
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
