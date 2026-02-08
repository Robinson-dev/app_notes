plugins {
    // Plugin de aplicación Android
    id("com.android.application")

    // Plugin de Kotlin para Android
    id("org.jetbrains.kotlin.android")

    // KSP (Kotlin Symbol Processing) para Room
    id("com.google.devtools.ksp")

    // Plugin de serialización para Supabase
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.tuapp.notes"  // Cambiar por tu package name
    compileSdk = 34                // SDK de compilación

    defaultConfig {
        applicationId = "com.tuapp.notes"  // ID único de tu app
        minSdk = 26                        // Android 8.0 mínimo
        targetSdk = 34                     // SDK objetivo
        versionCode = 1                    // Versión numérica
        versionName = "1.0"                // Versión legible

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Configuración para Compose
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        // Configuración para Release (producción)
        release {
            isMinifyEnabled = false  // Ofuscación de código
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Configuración de Java
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    // Configuración de Kotlin
    kotlinOptions {
        jvmTarget = "1.8"
    }

    // Habilitar Jetpack Compose
    buildFeatures {
        compose = true
    }

    // Versión del compilador de Compose
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    // Excluir recursos duplicados (evita errores de build)
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // ============================================
    // ANDROID CORE
    // ============================================
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // ============================================
    // JETPACK COMPOSE (UI)
    // ============================================
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // ============================================
    // ROOM (Base de datos local)
    // ============================================
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")  // Procesador de anotaciones

    // ============================================
    // SUPABASE (Backend remoto)
    // ============================================
    implementation(platform("io.github.jan-tennert.supabase:bom:2.0.0"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")  // Opcional

    // ============================================
    // KTOR (Cliente HTTP para Supabase)
    // ============================================
    implementation("io.ktor:ktor-client-android:2.3.7")
    implementation("io.ktor:ktor-client-core:2.3.7")
    implementation("io.ktor:ktor-utils:2.3.7")

    // ============================================
    // SERIALIZACIÓN (para Supabase)
    // ============================================
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

    // ============================================
    // CORRUTINAS (Programación asíncrona)
    // ============================================
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // ============================================
    // LIFECYCLE & VIEWMODEL
    // ============================================
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    // ============================================
    // TESTING (opcional pero recomendado)
    // ============================================
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // ============================================
    // DEBUG (herramientas de desarrollo)
    // ============================================
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}