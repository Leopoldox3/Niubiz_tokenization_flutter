package pe.mobile.cuy.visanet_tokenization.result

import android.os.Bundle
import org.json.JSONObject
import pe.mobile.cuy.visanet_tokenization.model.ResultMessage

class DefaultResultProcessor : ResultProcessor {
    override fun parse(data: Bundle?): ResultMessage {
        return try {
            val response = data?.getString("keySuccess")!!
            val jsonResult = JSONObject(response)

            val errorCode = jsonResult.getInt("errorCode")
            val errorMessage = jsonResult.getString("errorMessage") ?: ""

            if (errorCode == 0 && errorMessage == "OK")
                ResultMessage.success("OK", response)
            else
                ResultMessage.failure(errorMessage)
        } catch (e: Exception) {
            ResultMessage.failure()
        }
    }
}
