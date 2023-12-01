plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.jcasrui.articulos"
    compileSdk = 34 // pasamos del 33 al 34 para que compile el proyecto

    defaultConfig {
        applicationId = "com.jcasrui.articulos"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    // Activamos el viewBinding y el dataBinding
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    // Libreria abdulais CircularImageView
    /*allprojects {
        repositories {
            maven {
                url "https://jitpack.io" }
        }
    }*/
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")  // Esta versión de kotlin debe ser la misma que en build gradle del project
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // GitHub CircularImageView
    //implementation("com.github.abdularis:circularimageview:<latest-version>")
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Declare the dependency for the Firebase Authentication library
    implementation("com.google.firebase:firebase-auth-ktx")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    //implementation (":domain:entity")

    //libreria de animaciones Lottie
    val lottieVersion = "3.4.0"
    implementation("com.airbnb.android:lottie:$lottieVersion")
}