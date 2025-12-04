plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.compose.ecommerceapp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.compose.ecommerceapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //for image loading using coil in compose
    implementation("io.coil-kt.coil3:coil-compose:3.0.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.0")

    //navigation
    //val nav_version = "2.9.6"
    //implementation("androidx.navigation:navigation-compose:$nav_version")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:34.5.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

//    //hilt
//    implementation("com.google.dagger:hilt-android:2.57.1")
//    ksp("com.google.dagger:hilt-android-compiler:2.57.1")
//
//    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
//
//    //room
//    val room_version = "2.8.3"
//    implementation("androidx.room:room-runtime:$room_version")
//    ksp("androidx.room:room-compiler:$room_version")
//    implementation("androidx.room:room-ktx:${room_version}")
//
//    //coroutine
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    // Firebase (unchanged; recent BOM)
    implementation(platform("com.google.firebase:firebase-bom:34.5.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    // Hilt (bump to latest stable for minor fixes)
    implementation("com.google.dagger:hilt-android:2.57.2")
    ksp("com.google.dagger:hilt-compiler:2.57.2")  // Note: Use hilt-compiler, not android-compiler for KSP

    // CRITICAL FIX: Update to latest stable for SavedStateHandle + Navigation support
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Room (downgrade to stable if 2.8.3 is alpha/beta; latest stable is 2.6.1)
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // Coroutines (major update: 1.3.9 -> 1.8.1 for Flow/StateFlow stability)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // STRONGLY RECOMMENDED: Add Compose BOM for automatic version alignment (prevents mismatches)
    implementation(platform("androidx.compose:compose-bom:2025.11.00"))

    // Then, for Navigation/Lifecycle (BOM-managed; add if missing)
    implementation("androidx.navigation:navigation-compose")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose")
    implementation("androidx.activity:activity-compose")

    // Restore Material 2 icons (deprecated but functional for now)
    implementation("androidx.compose.material:material-icons-core")

    implementation("com.google.android.gms:play-services-base:18.4.0")

    implementation("androidx.datastore:datastore-preferences:1.0.0")


}