package config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.Principal
import io.ktor.server.auth.jwt.JWTAuthenticationProvider
import java.util.Date

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
object JwtConfig {

    private val secret = EnvConfig.jwtSecret
    private val issuer = EnvConfig.jwtIssuer
    private val audience = EnvConfig.jwtAudience
    private val validityInMs = EnvConfig.jwtValidityMs

    private val algorithm = Algorithm.HMAC256(secret)

    fun generateToken(userId: String): String =
        JWT.create()
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("userId", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + validityInMs))
            .sign(algorithm)

    fun configureKtorFeature(config: JWTAuthenticationProvider.Config) {
        config.verifier(
            JWT
                .require(algorithm)
                .withIssuer(issuer)
                .withAudience(audience)
                .build()
        )
        config.validate { credential ->
            val userId = credential.payload.getClaim("userId").asString()
            if (userId != null) UserPrincipal(userId) else null
        }
    }
}

data class UserPrincipal(val userId: String) : Principal