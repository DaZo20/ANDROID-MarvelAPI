import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinx.serialization.json)
}

val localProperties = Properties().apply {
    load(File(rootProject.projectDir, "local.properties").inputStream())
}

android {
    namespace = "com.dmolaya.dev.marvelapi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dmolaya.dev.marvelapi"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "PUBLIC_KEY", "\"${localProperties["PUBLIC_KEY"]}\"")
            buildConfigField("String", "PRIVATE_KEY", "\"${localProperties["PRIVATE_KEY"]}\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "PUBLIC_KEY", "\"${localProperties["PUBLIC_KEY"]}\"")
            buildConfigField("String", "PRIVATE_KEY", "\"${localProperties["PRIVATE_KEY"]}\"")
        }
    }

    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/NOTICE.md"
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //ExtendedIcons
    implementation(libs.icons.core)
    implementation(libs.icons.extended)

    //ConstraintLayout
    implementation(libs.constraint.layout)

    //Retrofit
    implementation(libs.retrofit.retrofit2)
    implementation(libs.retrofit.converter)

    //OkHttp3 Logging
    implementation(libs.okhttp.logging)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    //Coil
    implementation(libs.coil.compose)

    //Navigation Compose
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //Paging3
    implementation(libs.paging.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Androidx Arch Test
    testImplementation(libs.androidx.arch.core.testing)
    //Mockk
    testImplementation(libs.mockk.core)
    androidTestImplementation(libs.mockk.android)

    //Hilt Test
    kspTest(libs.hilt.compiler)
    kspAndroidTest(libs.hilt.compiler)
    testImplementation(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.testing)

    //Coroutines Test
    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
}