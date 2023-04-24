package pe.mobile.cuy.visanet_tokenization.reflection

import lib.visanet.com.pe.visanetlib.presentation.custom.VisaNetViewAuthorizationCustom

/* Setter method "isLogoTextMerchant" was removed
 * Java Reflection is used to solve this!
*/
var VisaNetViewAuthorizationCustom.isLogoTextMerchantExt: Boolean
    get() = this.isLogoTextMerchant
    set(value) {
        val field = this.javaClass.getDeclaredField("logoTextMerchant")
        field.isAccessible = true
        field.set(this, value)
    }
