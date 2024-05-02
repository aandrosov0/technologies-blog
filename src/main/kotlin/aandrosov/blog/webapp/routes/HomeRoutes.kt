package aandrosov.blog.webapp.routes

import aandrosov.blog.webapp.daos.daoArticle
import aandrosov.blog.webapp.daos.daoCategory
import aandrosov.blog.webapp.models.UserSession
import aandrosov.blog.webapp.paginate
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.math.ceil

const val MAX_ARTICLES_COUNT = 6

fun Application.homeRoutes() {
    routing {
        getHomeRoute()
    }
}

fun Route.getHomeRoute() = get {
    var articles = daoArticle.all()

    val query = call.parameters["query"]
    if (query != null) {
        val regex = Regex("\\W*((?i)${query.lowercase()}(?-i))\\W*")
        articles = articles.filter { regex.containsMatchIn(it.title.lowercase()) }
    }

    val category = call.parameters["category"]
    if (category != null) {
        articles = articles.filter { it.category == category }
    }

    var page = call.parameters["page"]?.toIntOrNull() ?: 1
    if (page < 0) {
        page = 1
    }

    val pages = ceil(articles.size / MAX_ARTICLES_COUNT.toDouble())
    articles = paginate(articles, page, MAX_ARTICLES_COUNT)

    val model = mapOf(
        "articles" to articles,
        "categories" to daoCategory.all(),
        "page" to page,
        "pages" to pages)
    call.respond(FreeMarkerContent("home.html", model))
}