package pe.mobile.cuy.visanet_tokenization.client

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthenticationService {
    @Headers("Accept: text/plain")
    @POST(value = "/api.security/v1/security")
    fun getToken(@Header("Authorization") auth: String): Call<ResponseBody>
}