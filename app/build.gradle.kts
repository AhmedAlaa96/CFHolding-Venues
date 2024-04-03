plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.ahmed.cfholding_venues"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ahmed.cfholding_venues"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("../keystore/cf-holding-venues.jks")
            storePassword = "venues123456"
            keyAlias = "venues"
            keyPassword = "venues123456"

        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    flavorDimensions("api")
    productFlavors {
        create("live") {
            buildConfigField("String", "BASE_NETWORK_URL", "\"https://api.foursquare.com/\"")
            buildConfigField(
                "String",
                "CLIENT_ID",
                "\"4EQRZPSGKBZGFSERGJY055FRW2OSPJRZYR4C3J0JN2CQQFIV\""
            )
            buildConfigField(
                "String",
                "CLIENT_SECRET",
                "\"AJR4B5LLRONWAJWJJOACHAFLCWS2YJAZMGQNFFZQP0IB3THR\""
            )
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    secrets {
        // Optionally specify a different file name containing your secrets.
        // The plugin defaults to "local.properties"
        propertiesFileName = "secrets.properties"

        // A properties file containing default secret values. This file can be
        // checked in version control.
        defaultPropertiesFileName = "local.defaults.properties"

        // Configure which keys should be ignored by the plugin by providing regular expressions.
        // "sdk.dir" is ignored by default.
        ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
        ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.swipeToRefresh)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)
    kapt(libs.room.compiler)
    kapt(libs.metadata.jvm)

    implementation(libs.retrofit)
    implementation(libs.retrofit.logger)
    implementation(libs.retrofit.converter)
    implementation(libs.retrofit.scalars)

    implementation(libs.glide)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compile)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.kotlin.nhaarman)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.arch.core)
    testImplementation(libs.kotlinx.corutines.test)

    androidTestImplementation(libs.idling.resource)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.barista) {
        exclude(group = "org.jetbrains.kotlin")
    }
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}