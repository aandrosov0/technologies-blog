package aandrosov.blog.webapp.daos

import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable() {
    val name = varchar("name", 16).uniqueIndex()
    val password = varchar("password", 32)
    val role = varchar("role", 8)
}