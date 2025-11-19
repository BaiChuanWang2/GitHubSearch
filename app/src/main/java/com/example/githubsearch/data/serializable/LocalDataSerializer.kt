package com.example.githubsearch.data.serializable

import androidx.datastore.core.Serializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object LocalDataSerializer: Serializer<LocalData> {
    override val defaultValue: LocalData
        get() = LocalData()

    override suspend fun readFrom(input: InputStream): LocalData {
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

    override suspend fun writeTo(t: LocalData, output: OutputStream) {
        output.bufferedWriter().use { it.write(Json.encodeToString(t)) }
    }
}