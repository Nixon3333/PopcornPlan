plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlinSerialization)
    application
}

application {
    mainClass.set("com.drygin.backend.ApplicationKt")
}

dependencies {
    implementation(project(":shared"))

    // Ktor
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.websockets)

    // JSON serialization
    implementation(libs.kotlinx.serialization.json)

    // Logging
    implementation(libs.logback.classic)

    // Exposed (ORM)
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)

    // PostgreSQL driver
    implementation(libs.postgresql)

    // HikariCP (connection pool)
    implementation(libs.hikaricp)

    // Auth
    implementation(libs.ktor.auth)
    implementation(libs.ktor.auth.jwt)
    implementation(libs.jwt)

    // DotEnv
    implementation(libs.dotenv)

    // Koin
    implementation(libs.koin.ktor)
}
