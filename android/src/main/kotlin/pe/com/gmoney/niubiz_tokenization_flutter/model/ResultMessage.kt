package pe.mobile.cuy.visanet_tokenization.model

import java.io.Serializable

class ResultMessage(private val resultCode: String = "",
                    private val message: String = "",
                    private val data: String = "") : Serializable {

    companion object {
        fun success(message: String = "", data: String = ""): ResultMessage {
            return ResultMessage("SUCCESS", message, data)
        }

        fun failure(message: String = ""): ResultMessage {
            return ResultMessage("ERROR", message)
        }

        fun userCanceled(): ResultMessage {
            return ResultMessage("USER_CANCELED")
        }
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
                "resultCode" to resultCode,
                "message" to message,
                "data" to data
        )
    }
}