plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.navigation.safe.args)
}

android {
    namespace = "com.example.todoofflineappmvvm"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.todoofflineappmvvm"
        minSdk = 23
        targetSdk = 35
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
        viewBinding = true
    }
    hilt {
        enableAggregatingTask = false
    }
}

dependencies {
    // Core and UI
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity)

    // ViewModel + LiveData
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime.ktx)

    //Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Retrofit, OkHttp, Moshi
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi.kotlin)


    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Coroutines
    implementation(libs.coroutines.android)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.javapoet)
    // MD3
    implementation(libs.material.v1100)
    implementation(libs.swiperefreshlayout)
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Testing coroutines
    testImplementation(libs.kotlinx.coroutines.test)

// MockK
    testImplementation(libs.mockk.v1139)

    // Room testing (instrumented tests)
    androidTestImplementation(libs.androidx.room.testing)

// AndroidX test core y runner
    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.androidx.runner)

// Hamcrest para asserts más expresivos
    androidTestImplementation(libs.hamcrest.library)
    testImplementation("com.google.truth:truth:1.1.3")

}
