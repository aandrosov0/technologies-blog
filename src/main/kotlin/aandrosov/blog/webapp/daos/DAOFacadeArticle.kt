package aandrosov.blog.webapp.daos

import aandrosov.blog.webapp.models.Article

interface DAOFacadeArticle {
    suspend fun all(): List<Article>
    suspend fun get(id: Int): Article?
    suspend fun add(title: String, content: String, imageUrl: String, category: String): Article?
    suspend fun update(id: Int, title: String, content: String, imageUrl: String, category: String): Boolean
    suspend fun delete(id: Int): Boolean
}