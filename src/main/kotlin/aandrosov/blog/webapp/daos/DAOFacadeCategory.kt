package aandrosov.blog.webapp.daos

import aandrosov.blog.webapp.models.Category

interface DAOFacadeCategory {
    suspend fun all(): List<Category>
    suspend fun get(name: String): Category?
    suspend fun add(name: String): Category?
    suspend fun update(name: String): Boolean
    suspend fun delete(name: String): Boolean
}