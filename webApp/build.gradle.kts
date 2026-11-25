import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

// Módulo de la versión WEB (WebAssembly). Usa el mismo código de "shared".
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig {
                outputFileName = "webApp.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        wasmJsMain.dependencies {
            implementation(project(":shared"))
            implementation(libs.compose.runtime)
            implementation(libs.compose.ui)
            implementation("org.jetbrains.kotlinx:kotlinx-browser:0.3")
        }
    }
}