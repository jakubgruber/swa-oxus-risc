package me.jakub.risc.model.request

import java.math.BigDecimal

data class ScoringRequest(

        var requestId: String? = null,
        val clientId: Int,
        val loanAmount: BigDecimal,
        val callbackUrl: String

)