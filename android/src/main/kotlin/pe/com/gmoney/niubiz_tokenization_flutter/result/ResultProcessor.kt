package pe.mobile.cuy.visanet_tokenization.result

import android.os.Bundle
import pe.mobile.cuy.visanet_tokenization.model.ResultMessage

interface ResultProcessor {
    fun parse(data: Bundle?): ResultMessage
}
