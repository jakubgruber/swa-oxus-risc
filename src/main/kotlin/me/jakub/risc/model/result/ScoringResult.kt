package me.jakub.risc.model.result

import java.math.BigDecimal

data class ScoringResult (

        var requestId: String? = null,
        val clientId: Int,
        val loanAmount: BigDecimal,
        var result: CustomerResultEnum? = null,
        val callbackUrl: String

)