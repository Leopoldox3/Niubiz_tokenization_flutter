package pe.mobile.cuy.visanet_tokenization.client

import android.util.Log
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class RestfulClient(private val baseUrl: String, private val auth: AuthorizationHeader? = null) {
    private val tag: String = "Client"

    fun post(path: String): String {
        var connection: HttpURLConnection? = null

        try {
            val url = "$baseUrl$path"
            val urlConnection = URL(url)
            connection = (urlConnection.openConnection() as HttpURLConnection)
            connection.requestMethod = "POST"

            if (auth != null) {
                connection.addRequestProperty("Authorization", auth.toString())
            }

            Log.i(tag, "--> POST $url")
            connection.connect()
            Log.i(tag, "<-- requestCode=${connection.responseCode}")
            if (connection.responseCode != HttpURLConnection.HTTP_CREATED) {
                throw Exception(connection.responseMessage)
            }

            val result: String = connection.inputStream.bufferedReader(StandardCharsets.UTF_8).use {
                it.readText()
            }
            Log.i(tag, "response OK: $result")

            return result
        } catch (e: Exception) {
            val errorStr = if (e.message != null) e.localizedMessage else "Unknown error"
            Log.i(tag, "response error: $errorStr")

            throw Exception(errorStr)
        } finally {
            Log.i(tag, "connection close")
            connection?.disconnect()
        }
    }
}