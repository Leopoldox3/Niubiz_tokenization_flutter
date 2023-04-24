package pe.mobile.cuy.visanet_tokenization.result

import android.os.Bundle
import org.json.JSONObject
import pe.mobile.cuy.visanet_tokenization.model.ResultMessage

class ErrorResultProcessor : ResultProcessor {
    override fun parse(data: Bundle?): ResultMessage {
        val response = data?.getString("keyError") ?: ""
        var result: ResultMessage

        var isValidJson = false
        try {
            val jsonResult = JSONObject(response)

            isValidJson = true

            val errorMessage = jsonResult.getString("errorMessage") ?: ""

            var actionDescription = ""
            if (jsonResult.has("order")) {
                val orderObject = jsonResult.getJSONObject("order")
                actionDescription = orderObject.getString("actionDescription") ?: ""
            }

            val errorDescription = errorMessage + (if (actionDescription.isEmpty()) ""
            else ": $actionDescription")

            result = ResultMessage.failure(errorDescription)
        } catch (e: Exception) {
            result = if (isValidJson)
                ResultMessage.failure()
            else
                ResultMessage.failure(response)
        }

        return result
    }
}