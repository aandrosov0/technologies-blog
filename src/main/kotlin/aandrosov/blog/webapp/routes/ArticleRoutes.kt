package aandrosov.blog.webapp.routes

import aandrosov.blog.webapp.daos.daoArticle
import aandrosov.blog.webapp.daos.daoCategory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking

fun Application.articleRoutes() {
    routing {
        getArticleRoute()

        authenticate("auth-session") {
            getArticleDeleteRoute()
            getArticleAddRoute()
            getArticleUpdateRoute()
        }
    }
}

fun Route.getArticleRoute() = get(Regex("/article/(?<id>\\d+)")) {
    val id = call.parameters["id"]

    try {
        val article = daoArticle.get(id!!.toInt()) ?: return@get call.respond(
            HttpStatusCode.NotFound,
            FreeMarkerContent("error.html", mapOf("message" to "Ошибка 404.", "description" to "Статья не найдена!"))
        )
        val model = mapOf("article" to article, "categories" to daoCategory.all())
        call.respond(FreeMarkerContent("article.html", model))
    } catch (_: NumberFormatException) {
        call.respond(
            HttpStatusCode.BadRequest,
            FreeMarkerContent("error.html", mapOf("message" to "Ошибка 400.", "description" to "Неверный запрос!"))
        )
    }
}

fun Route.getArticleDeleteRoute() = post(Regex("/article/delete/(?<id>\\d+)")) {
    val id = call.parameters["id"]

    try {
        val idNumber = id!!.toInt()
        val category = daoArticle.get(idNumber)!!.category

        if (!daoArticle.delete(idNumber)) {
            return@post call.respond(
                HttpStatusCode.NotFound,
                FreeMarkerContent("error.html", mapOf("message" to "Ошибка 404.", "description" to "Статья не найдена!")))
        }

        if (daoArticle.all().none { it.category == category }) {
            daoCategory.delete(category)
        }
        call.respondRedirect("/admin/articles")
    } catch (_: NumberFormatException) {
        call.respond(
            HttpStatusCode.BadRequest,
            FreeMarkerContent("error.html", mapOf("message" to "Ошибка 400.", "description" to "Неверный запрос!"))
        )
    }
}

fun Route.getArticleAddRoute() = post("/article") {
    val parameters = call.receiveParameters()
    val title = parameters["title"]
    val imageUrl = parameters["imageUrl"]
    val content = parameters["content"]
    val category = parameters["category"]

    if (title.isNullOrBlank() || imageUrl.isNullOrBlank() || content.isNullOrBlank() || category.isNullOrBlank()) {
        return@post call.respond(HttpStatusCode.BadRequest)
    }

    daoCategory.get(category) ?: daoCategory.add(category)
    val id = daoArticle.add(title, imageUrl, content, category)!!.id
    call.respondRedirect("/article/$id")
}

fun Route.getArticleUpdateRoute() = post("/article/{id}") {
    val parameters = call.receiveParameters()
    val id = call.parameters["id"]
    val title = parameters["title"]
    val imageUrl = parameters["imageUrl"]
    val content = parameters["content"]
    val category = parameters["category"]

    if (id.isNullOrBlank() || title.isNullOrBlank() || imageUrl.isNullOrBlank() || content.isNullOrBlank() || category.isNullOrBlank()) {
        return@post call.respond(HttpStatusCode.BadRequest)
    }

    val article = daoArticle.get(id.toInt()) ?: return@post call.respond(HttpStatusCode.BadRequest)
    daoCategory.get(category) ?: daoCategory.add(category)
    daoArticle.update(id.toInt(), title, content, imageUrl, category)

    if (daoArticle.all().none { it.category == article.category }) {
        daoCategory.delete(article.category)
    }

    call.respondRedirect("/article/$id")
}