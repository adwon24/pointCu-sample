plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.pointcu.sample"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.pointcu.sample"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.fragment)

    implementation("io.github.adwon24:pointcu:0.0.2.0-alpha")
    implementation("com.igaworks.ssp:IgawAdPopcornSSP:3.10.1")
    implementation(platform("com.naver.gfpsdk:nam-bom:8.10.3"))
    implementation("com.naver.gfpsdk:nam-core")
    implementation("com.naver.gfpsdk.mediation:nam-nda")
    implementation("com.applovin:applovin-sdk:13.6.2")
    implementation("com.vungle:vungle-ads:7.7.2")
    implementation("com.pangle.global:pag-sdk:7.9.1.3")
    implementation("com.fyber:marketplace-sdk:8.4.1")
    //implementation("com.google.android.gms:play-services-ads:24.8.0") // meta-data 키 없으므로 주석처리. AdMob 키 있을 경우 manifest에 등록 후 주석 제거
    implementation("androidx.annotation:annotation:1.4.0")
    implementation("com.facebook.android:audience-network-sdk:6.21.0")
}