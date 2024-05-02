package aandrosov.blog.webapp.daos

import aandrosov.blog.webapp.models.User
import aandrosov.blog.webapp.models.UserRole
import aandrosov.blog.webapp.daos.Database.dbQuery
import aandrosov.blog.webapp.md5
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

val daoUser: DAOFacadeUser = DAOFacadeUserImpl().apply {
    runBlocking {
        if (all().isEmpty()) {
            add("admin", md5("admin"), UserRole.ADMIN.name)
        }
    }
}

class DAOFacadeUserImpl : DAOFacadeUser {
    private fun convertRowToUser(row: ResultRow) = User(
        id = row[Users.id].value,
        name = row[Users.name],
        password = row[Users.password],
        role = UserRole.valueOf(row[Users.role])
    )

    override suspend fun all(): List<User> = dbQuery {
        Users
            .selectAll()
            .map(::convertRowToUser)
    }

    override suspend fun get(id: Int) = dbQuery {
        Users
            .select { Users.id eq id }
            .map(::convertRowToUser)
            .singleOrNull()
    }

    override suspend fun get(name: String) = dbQuery {
        Users
            .select { Users.name eq name }
            .map(::convertRowToUser)
            .singleOrNull()
    }

    override suspend fun add(name: String, password: String, role: String) = dbQuery {
        val insertStatement = Users.insert {
            it[Users.name] = name
            it[Users.password] = password
            it[Users.role] = role
        }

        insertStatement.resultedValues?.map(::convertRowToUser)?.singleOrNull()
    }

    override suspend fun update(id: Int, name: String, password: String, role: String) = dbQuery {
        Users.update({ Users.id eq id }) {
            it[Users.name] = name
            it[Users.password] = password
            it[Users.role] = role
        } > 0
    }

    override suspend fun delete(id: Int) = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }
}