package aandrosov.blog.webapp.routes

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.staticRoutes() {
    routing {
        staticResources("/static", "static")
        staticResources("/about", "static", "about.html")
        staticResources("/contacts", "static", "contacts.html")
    }
}