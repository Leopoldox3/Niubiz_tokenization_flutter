package pe.mobile.cuy.visanet_tokenization

import android.app.Activity
import android.content.Intent
import android.os.StrictMode
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry
import io.flutter.plugin.common.PluginRegistry.Registrar
import pe.mobile.cuy.visanet_tokenization.client.AuthenticationManager
import pe.mobile.cuy.visanet_tokenization.model.ResultMessage
import pe.mobile.cuy.visanet_tokenization.model.TokenRequest
import pe.mobile.cuy.visanet_tokenization.presenter.TokenizationUiPresenter
import pe.mobile.cuy.visanet_tokenization.presenter.TokenizationValues
import pe.mobile.cuy.visanet_tokenization.result.ResultProcessorFactory

/**
 * VisanetTokenizationPlugin
 * @author Pedro Luis <pedro@cuy.pe>
 */

public class VisanetTokenizationPlugin : FlutterPlugin, MethodCallHandler, ActivityAware, PluginRegistry.ActivityResultListener {

    companion object {
        const val TAG = "VisaNetTokenization"
        const val CHANNEL_NAME: String = "visanet_tokenization"

        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), CHANNEL_NAME)
            channel.setMethodCallHandler(VisanetTokenizationPlugin())
        }
    }

    private var channel: MethodChannel? = null
    private var channelResult: Result? = null
    private var activity: FlutterActivity? = null
    private var flutterBinding: FlutterPlugin.FlutterPluginBinding? = null
    private var activityBinding: ActivityPluginBinding? = null

    //FlutterPlugin
    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        flutterBinding = flutterPluginBinding
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        flutterBinding = null
    }

    //ActivityAware
    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        channel = MethodChannel(flutterBinding?.binaryMessenger, CHANNEL_NAME)
        channel?.setMethodCallHandler(this)

        activityBinding = binding
        activityBinding?.addActivityResultListener(this)
        activity = binding.activity as FlutterActivity

        //strict policy - permit all
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    override fun onDetachedFromActivity() {
        channel?.setMethodCallHandler(null)
        channel = null
        channelResult = null

        activityBinding?.removeActivityResultListener(this)
        activityBinding = null

        activity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity()
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        channelResult = result

        try {
            val username = call.argument<String>("username") ?: ""
            val password = call.argument<String>("password") ?: ""

            val merchantId = call.argument<String>("merchantId") ?: ""
            val endPoint = call.argument<String>("endPoint") ?: ""
            val endPointExt = endPoint + (if (endPoint.endsWith("/")) "" else "/")

            val name = call.argument<String>("name") ?: ""
            val lastName = call.argument<String>("lastName") ?: ""
            val email = call.argument<String>("email") ?: ""

            val logoTitle = call.argument<String>("logoTitle") ?: ""

            val authManager = AuthenticationManager(endPoint)
            val request = TokenRequest(username, password, merchantId)
            authManager.getSecurityToken(request, success = { response ->
                val values = TokenizationValues(
                        title = logoTitle,
                        baseUrl = endPointExt,
                        merchantId = merchantId,
                        securityToken = response.token,
                        holderName = name,
                        holderLastName = lastName,
                        holderEmail = email
                )

                val presenter = TokenizationUiPresenter(activity, values)
                presenter.show()
            }, failure = { error ->
                returnResult(ResultMessage.failure(error))
            })
        } catch (e: Exception) {
            Log.e(TAG, "error: $e")
            returnResult(ResultMessage.failure())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (TokenizationUiPresenter.isValidRequest(requestCode)) {
            val factory = ResultProcessorFactory()
            if (data != null) {
                val resultProcessor = if (resultCode == Activity.RESULT_OK)
                    factory.defaultProcessor()
                else
                    factory.errorProcessor()

                val result = resultProcessor.parse(data.extras)
                returnResult(result)
            } else {
                Log.i(TAG, "user canceled")
                returnResult(ResultMessage.userCanceled())
            }

            return true
        }

        return false
    }

    private fun returnResult(result: ResultMessage) {
        channelResult?.success(result.toMap())
    }
}