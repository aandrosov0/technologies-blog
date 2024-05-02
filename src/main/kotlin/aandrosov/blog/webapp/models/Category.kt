package aandrosov.blog.webapp.models

data class Category(
    val name: String
) {
    override fun toString(): String = name
}