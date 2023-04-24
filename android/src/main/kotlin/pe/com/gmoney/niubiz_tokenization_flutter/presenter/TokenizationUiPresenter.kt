package pe.mobile.cuy.visanet_tokenization.presenter

import io.flutter.embedding.android.FlutterActivity
import lib.visanet.com.pe.visanetlib.VisaNet
import lib.visanet.com.pe.visanetlib.data.custom.Channel
import lib.visanet.com.pe.visanetlib.presentation.custom.VisaNetViewAuthorizationCustom
import pe.mobile.cuy.visanet_tokenization.reflection.isLogoTextMerchantExt

class TokenizationUiPresenter(private val activity: FlutterActivity?, private val values: TokenizationValues) {

    companion object {
        fun isValidRequest(requestCode: Int): Boolean =
                (requestCode == VisaNet.VISANET_TOKENIZATION)
    }

    private val parameters: Map<String, Any?>
        get() {
            val parameters = HashMap<String, Any?>()
            parameters[VisaNet.VISANET_CHANNEL] = Channel.MOBILE
            parameters[VisaNet.VISANET_MERCHANT] = values.merchantId
            parameters[VisaNet.VISANET_ENDPOINT_URL] = values.baseUrl

            parameters[VisaNet.VISANET_PURCHASE_NUMBER] = System.currentTimeMillis().toString().substring(0, 12)

            parameters[VisaNet.VISANET_TOKENIZATION_NAME] = values.holderName
            parameters[VisaNet.VISANET_TOKENIZATION_LASTNAME] = values.holderLastName
            parameters[VisaNet.VISANET_TOKENIZATION_EMAIL] = values.holderEmail

            parameters[VisaNet.VISANET_SECURITY_TOKEN] = values.securityToken
            parameters[VisaNet.VISANET_TOKENIZATION_AMOUNT] = 1.0

            return parameters
        }

    private val custom: VisaNetViewAuthorizationCustom
        get() {
            val custom = VisaNetViewAuthorizationCustom()
            custom.isLogoTextMerchantExt = true
            custom.logoTextMerchantText = values.title
            return custom
        }

    fun show() {
        try {
            VisaNet.tokenization(activity, parameters, custom)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}