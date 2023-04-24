package pe.mobile.cuy.visanet_tokenization.presenter

data class TokenizationValues(
        val merchantId: String,
        val securityToken: String,

        val holderName: String,
        val holderLastName: String,
        val holderEmail: String,

        val title: String,
        val baseUrl: String
)