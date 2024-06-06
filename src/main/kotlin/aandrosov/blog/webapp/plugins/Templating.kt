package aandrosov.blog.webapp.plugins

import freemarker.cache.ClassTemplateLoader
import io.ktor.server.application.*
import io.ktor.server.freemarker.*

fun Application.configureTemplating() {
    install(FreeMarker) {
        defaultEncoding = "UTF-8"
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
}