package auth

/**
 * Created by Drygin Nikita on 25.07.2025.
 */
object AuthService {
    fun validateUser(userId: String): Boolean {
        // Проверка в БД: уже авторизован, забанен и т.д.
        return userId.isNotBlank()
    }
}
