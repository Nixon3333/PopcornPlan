package config

import io.github.cdimascio.dotenv.dotenv

/**
 * Created by Drygin Nikita on 26.07.2025.
 */
object EnvConfig {
    private val dotenv = dotenv {
        directory = "backend"
    }

    val dbUrl: String = dotenv["DB_URL"]
    val dbUser: String = dotenv["DB_USER"]
    val dbPassword: String = dotenv["DB_PASSWORD"]

    val jwtSecret = dotenv["JWT_SECRET"] ?: "not-secret-key"
    val jwtIssuer = dotenv["JWT_ISSUER"] ?: "popcorn"
    val jwtAudience = dotenv["JWT_AUDIENCE"] ?: "popcorn-users"
    val jwtValidityMs = dotenv["JWT_VALIDITY_MS"]?.toLongOrNull() ?: 86_400_000L
}