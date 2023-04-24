package pe.mobile.cuy.visanet_tokenization.client

import android.util.Base64
import java.nio.charset.StandardCharsets

interface AuthorizationHeader {
    override fun toString(): String
}

class BasicAuthorizationHeader(private val username: String, private val password: String)
    : AuthorizationHeader {
    override fun toString(): String {
        return "Basic " + Base64.encodeToString(
                "$username:$password".toByteArray(StandardCharsets.UTF_8),
                Base64.NO_WRAP
        )
    }
}