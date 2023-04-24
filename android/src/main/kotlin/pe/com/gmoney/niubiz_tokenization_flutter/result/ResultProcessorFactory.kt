package pe.mobile.cuy.visanet_tokenization.result

class ResultProcessorFactory {
    fun defaultProcessor(): ResultProcessor {
        return DefaultResultProcessor()
    }

    fun errorProcessor(): ResultProcessor {
        return ErrorResultProcessor()
    }
}
