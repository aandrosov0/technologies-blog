package aandrosov.blog.webapp

import java.security.MessageDigest
import kotlin.math.min

fun <T> paginate(list: List<T>, page: Int, pageSize: Int): List<T> {
    if (page <= 0 || pageSize <= 0) {
        throw IllegalArgumentException()
    }

    val fromIndex = (page - 1) * pageSize
    if (list.size < fromIndex) {
        return emptyList()
    }

    return list.subList(fromIndex, min(fromIndex + pageSize, list.size))
}

@OptIn(ExperimentalStdlibApi::class)
fun md5(data: String): String =
    MessageDigest
        .getInstance("MD5")
        .digest(data.toByteArray(Charsets.UTF_8))
        .toHexString()