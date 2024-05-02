package aandrosov.blog.webapp.models

data class User(
    val id: Int,
    val name: String,
    val password: String,
    val role: UserRole
)
