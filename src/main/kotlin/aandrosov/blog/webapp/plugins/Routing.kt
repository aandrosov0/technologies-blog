package aandrosov.blog.webapp.plugins

import aandrosov.blog.webapp.routes.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    staticRoutes()
    loginRoutes()
    homeRoutes()
    adminRoutes()
    articleRoutes()
}