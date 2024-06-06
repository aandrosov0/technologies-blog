
package aandrosov.blog.webapp

import aandrosov.blog.webapp.daos.Database
import aandrosov.blog.webapp.plugins.*
import aandrosov.blog.webapp.plugins.configureSessions
import io.ktor.server.application.*

//resources/application.conf
@Suppress("unused")
fun Application.module() {
    configureSessions()
    configureAuthentication()
    configureTemplating()
    Database.init()
    configureRouting()
}