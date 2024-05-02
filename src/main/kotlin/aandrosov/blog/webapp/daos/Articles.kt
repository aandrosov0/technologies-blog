package aandrosov.blog.webapp.daos

import org.jetbrains.exposed.dao.id.IntIdTable

object Articles: IntIdTable() {
    val title = varchar("title", 128)
    val content = text("content")
    val imageUrl = text("image_url")
    val category = reference("category", Categories.name)
}