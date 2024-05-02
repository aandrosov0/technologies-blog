package aandrosov.blog.webapp.daos

import aandrosov.blog.webapp.daos.Database.dbQuery
import aandrosov.blog.webapp.models.Article
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

val daoArticle: DAOFacadeArticle = DAOFacadeArticleImpl().apply {
    runBlocking {
        if (all().isEmpty()) {
            daoCategory.add("Технологии")
            daoCategory.add("Инноватика")
            daoCategory.add("Программирование")
            daoCategory.add("Образование")

            add(
                "Искусственный интеллект и машинное обучение: будущее образования и бизнеса",
                javaClass.classLoader.getResourceAsStream("article.txt")?.readAllBytes()!!.decodeToString(),
                "https://static.timesofisrael.com/www/uploads/2019/12/iStock-1029035836-e1575983057612.jpg",
                "Технологии")

            add(
                "Искусственный интеллект и машинное обучение: будущее образования и бизнеса",
                javaClass.classLoader.getResourceAsStream("article.txt")?.readAllBytes()!!.decodeToString(),
                "https://static.timesofisrael.com/www/uploads/2019/12/iStock-1029035836-e1575983057612.jpg",
                "Технологии")

            add(
                "Технологии: путь к прогрессу и улучшению качества жизни",
                javaClass.classLoader.getResourceAsStream("article.txt")?.readAllBytes()!!.decodeToString(),
                "https://klike.net/uploads/posts/2022-09/1662526332_s-69.jpg",
                "Инноватика")

            add(
                "Программирование: ключ к автоматизации и инновациям в XXI веке",
                javaClass.classLoader.getResourceAsStream("article2.txt")?.readAllBytes()!!.decodeToString(),
                "https://media.rbcdn.ru/media/news/dmitrygavrilov-065_hSJGQrX.jpg",
                "Программирование")

            for (i in 1..10) {
                add(
                    "Образование в России: структура, уровни и особенности",
                    javaClass.classLoader.getResourceAsStream("article3.txt")?.readAllBytes()!!.decodeToString(),
                    "https://vsvoemdome.ru/wp-content/uploads/2021/03/srednee-polnoe-obrazovanie.png",
                    "Образование")
            }
        }
    }
}

class DAOFacadeArticleImpl : DAOFacadeArticle {
    private fun convertRowToArticle(row: ResultRow) = Article(
        id = row[Articles.id].value,
        title = row[Articles.title],
        content = row[Articles.content],
        imageUrl = row[Articles.imageUrl],
        category = row[Articles.category]
    )

    override suspend fun all() = dbQuery {
        Articles.selectAll().map(::convertRowToArticle)
    }

    override suspend fun get(id: Int) = dbQuery {
        Articles
            .select {Articles.id eq id}
            .map(::convertRowToArticle)
            .singleOrNull()
    }

    override suspend fun add(title: String, content: String, imageUrl: String, category: String) = dbQuery {
        val insertStatement = Articles.insert {
            it[Articles.title] = title
            it[Articles.content] = content
            it[Articles.imageUrl] = imageUrl
            it[Articles.category] = category
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::convertRowToArticle)
    }

    override suspend fun update(id: Int, title: String, content: String, imageUrl: String, category: String) = dbQuery {
        Articles.update({ Articles.id eq id }) {
            it[Articles.title] = title
            it[Articles.content] = content
            it[Articles.imageUrl] = imageUrl
            it[Articles.category] = category
        } > 0
    }

    override suspend fun delete(id: Int) = dbQuery {
        Articles.deleteWhere { Articles.id eq id } > 0
    }
}