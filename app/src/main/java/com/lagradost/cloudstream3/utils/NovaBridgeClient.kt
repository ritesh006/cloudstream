package com.lagradost.cloudstream3.utils

import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.mvvm.logError
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import org.json.JSONObject

object NovaBridgeClient {
    var novaUrl: String = http://192.168.1.9:8765
    var authToken: String = nova-cloudstream-secure-token-2026

    suspend fun sendToNova(
        title: String,
        link: ExtractorLink,
    ): Boolean {
        return try {
            val payload = JSONObject().apply {
                put(title, title)
                put(url, link.url)
                put(referer, link.referer)
                put(quality, Qualities.getStringByInt(link.quality))
                put(type, link.type.name)
                val headersJson = JSONObject()
                link.headers.forEach { (k, v) -> headersJson.put(k, v) }
                put(headers, headersJson)
            }

            val response = app.post(
                /api/download,
                headers = mapOf(
                    Authorization to Bearer ,
                    Content-Type to application/json
                ),
                data = payload.toString()
            )
            response.isSuccessful
        } catch (e: Exception) {
            logError(e)
            false
        }
    }
}
