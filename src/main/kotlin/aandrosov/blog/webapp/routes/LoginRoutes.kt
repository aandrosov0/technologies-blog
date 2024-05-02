package aandrosov.blog.webapp.routes

import aandrosov.blog.webapp.models.UserSession
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.loginRoutes() {
    routing {
        getLoginPageRoute()

        authenticate("auth-form") {
            getLoginRoute()
        }
    }
}

fun Route.getLoginPageRoute() = get("/login") {
    call.respond(FreeMarkerContent("login.html", emptyMap<String, String>()))
}

fun Route.getLoginRoute() = post("/login") {
    val userName = call.principal<UserIdPrincipal>()?.name.toString()
    call.sessions.set(UserSession(userName))
    call.respondRedirect("/admin")
}
