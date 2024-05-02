package aandrosov.blog.webapp.plugins

import aandrosov.blog.webapp.daos.daoUser
import aandrosov.blog.webapp.md5
import aandrosov.blog.webapp.models.UserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*

fun Application.configureAuthentication() {
    install(Authentication) {
        configureFormAuthentication()
        configureSessionAuthentication()
    }
}

fun AuthenticationConfig.configureFormAuthentication() = form("auth-form") {
    userParamName = "username"
    passwordParamName = "password"

    validate {
        val user = daoUser.get(it.name)
        if (user != null && user.password == md5(it.password)) {
            return@validate UserIdPrincipal(it.name)
        }

        return@validate null
    }
    challenge {
        val model = mapOf("message" to "Данные неверные!")
        call.respond(HttpStatusCode.Unauthorized, FreeMarkerContent("login.html", model))
    }
}

fun AuthenticationConfig.configureSessionAuthentication() = session<UserSession>("auth-session") {
    validate {
        daoUser.get(it.name) ?: return@validate null
        it
    }
    challenge {
        call.respondRedirect("/login")
    }
}