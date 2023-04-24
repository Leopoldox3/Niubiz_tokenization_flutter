package pe.mobile.cuy.visanet_tokenization.client

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import pe.mobile.cuy.visanet_tokenization.model.TokenRequest
import pe.mobile.cuy.visanet_tokenization.model.TokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AuthenticationManager(private val baseUrl: String) {
    fun getSecurityToken(
            request: TokenRequest,
            success: (TokenResponse) -> Unit,
            failure: (String) -> Unit
    ) {
        val authHeader = BasicAuthorizationHeader(request.username, request.password)
        val auth = authHeader.toString()

        val client = createClient()

        val service = client.create(AuthenticationService::class.java)
        val call = service.getToken(auth)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val body = response.body()!!.string()
                    val result = String(body.toByteArray(Charsets.UTF_8))

                    success(TokenResponse(result))
                } else {
                    failure(response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                failure(t.localizedMessage ?: "")
            }
        })
    }

    private fun createClient(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.apply { level = HttpLoggingInterceptor.Level.BODY }

        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .build()
    }
}