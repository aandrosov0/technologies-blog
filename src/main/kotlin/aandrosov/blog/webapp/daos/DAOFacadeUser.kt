package aandrosov.blog.webapp.daos

import aandrosov.blog.webapp.models.User

interface DAOFacadeUser {
    suspend fun all(): List<User>
    suspend fun get(id: Int): User?
    suspend fun get(name: String): User?
    suspend fun add(name: String, password: String, role: String): User?
    suspend fun update(id: Int, name: String, password: String, role: String): Boolean
    suspend fun delete(id: Int): Boolean
}