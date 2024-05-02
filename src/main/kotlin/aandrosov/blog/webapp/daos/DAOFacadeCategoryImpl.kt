package aandrosov.blog.webapp.daos

import aandrosov.blog.webapp.daos.Database.dbQuery
import aandrosov.blog.webapp.models.Category
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

val daoCategory: DAOFacadeCategory = DAOFacadeCategoryImpl()

class DAOFacadeCategoryImpl : DAOFacadeCategory {
    private fun convertRowToCategory(row: ResultRow) = Category(
        name = row[Categories.name]
    )

    override suspend fun all() = dbQuery {
        Categories.selectAll().map(::convertRowToCategory)
    }

    override suspend fun get(name: String) = dbQuery {
        Categories
            .select {Categories.name eq name}
            .map(::convertRowToCategory)
            .singleOrNull()
    }

    override suspend fun add(name: String) = dbQuery {
        val insertStatement = Categories.insert {
            it[Categories.name] = name
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::convertRowToCategory)
    }

    override suspend fun update(name: String) = dbQuery {
        Categories.update({ Categories.name eq name }) {
            it[Categories.name] = name
        } > 0
    }

    override suspend fun delete(name: String) = dbQuery {
        Categories.deleteWhere { Categories.name eq name } > 0
    }
}