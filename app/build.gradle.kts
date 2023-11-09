plugins {
    id("com.android.application")
    id("com.google.gms.google-services") // Google services Plugin
}

android {
    namespace = "com.taskmuse.app"
    namespace = "com.taskmuse.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.taskmuse.app"
        applicationId = "com.taskmuse.app"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {


    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")

    // Navigation support
    implementation("androidx.navigation:navigation-runtime:2.7.5")
    implementation("androidx.navigation:navigation-fragment:2.7.5")

    // FIREBASE PACKAGES
    implementation ("com.google.firebase:firebase-database:20.3.0")
    implementation(platform("com.google.firebase:firebase-bom:32.4.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.firebase:firebase-auth:22.2.0")
    implementation("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-database")

}