package aandrosov.blog.webapp.plugins

import aandrosov.blog.webapp.models.UserSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*

fun Application.configureSessions() {
    install(Sessions) {
        configureUserSessionCookie()
    }
}

fun SessionsConfig.configureUserSessionCookie() = cookie<UserSession>("user_session") {
    cookie.path = "/"
    cookie.maxAgeInSeconds = 60 * 60 * 60 * 24
}