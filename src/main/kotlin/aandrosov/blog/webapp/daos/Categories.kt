package aandrosov.blog.webapp.daos

import org.jetbrains.exposed.sql.Table

object Categories: Table() {
    val name = varchar("name", 32)
    override val primaryKey = PrimaryKey(name)
}