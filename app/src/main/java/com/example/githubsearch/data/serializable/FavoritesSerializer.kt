package com.example.githubsearch.data.serializable

import androidx.datastore.core.Serializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object FavoritesSerializer : Serializer<Favorites> {
    override val defaultValue: Favorites
        get() = Favorites()

    override suspend fun readFrom(input: InputStream): Favorites {
        return try {
            val text = input.bufferedReader().use { it.readText() }
            if (text.isBlank()) {
                defaultValue
            } else {
                Json.decodeFromString(text)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Favorites, output: OutputStream) {
        output.bufferedWriter().use { it.write(Json.encodeToString(t)) }
    }
}