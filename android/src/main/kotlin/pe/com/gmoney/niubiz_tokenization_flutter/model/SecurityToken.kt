package pe.mobile.cuy.visanet_tokenization.model

data class TokenRequest(val username: String, val password: String, val merchantId: String)

data class TokenResponse(val token: String)