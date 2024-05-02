package aandrosov.blog.webapp.models

import io.ktor.server.auth.*

data class UserSession(
    val name: String,
) : Principal
