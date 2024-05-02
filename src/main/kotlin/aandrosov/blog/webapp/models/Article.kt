package aandrosov.blog.webapp.models

data class Article(
    val id: Int,
    val title: String,
    val content: String,
    val imageUrl: String,
    val category: String,
)