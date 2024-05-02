package aandrosov.blog.webapp.routes

import aandrosov.blog.webapp.daos.daoArticle
import aandrosov.blog.webapp.models.UserSession
import io.ktor.client.engine.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.adminRoutes() {
    routing {
        authenticate("auth-session") {
            getAdminRoute()
            getAdminArticlesRoute()
            getAdminEditRoute()
        }
    }
}

fun Route.getAdminRoute() = get("/admin") {
    val userSession = call.principal<UserSession>()
    call.respond(FreeMarkerContent("admin.html", mapOf("username" to userSession?.name)))
}

fun Route.getAdminArticlesRoute() = get("/admin/articles") {
    val userSession = call.principal<UserSession>()
    val articles = daoArticle.all()
    val model = mapOf("username" to userSession?.name, "articles" to articles)
    call.respond(FreeMarkerContent("articles.html", model))
}

fun Route.getAdminEditRoute() = get("/admin/edit/{id?}") {
    val id = call.parameters["id"]
    val userSession = call.principal<UserSession>()
    val model = mutableMapOf<String, Any?>("username" to userSession?.name)
    if (!id.isNullOrBlank()) {
        model["article"] = daoArticle.get(id.toInt())
    }

    call.respond(FreeMarkerContent("edit.html", model))
}