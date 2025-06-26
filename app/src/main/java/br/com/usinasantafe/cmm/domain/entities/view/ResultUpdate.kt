package br.com.usinasantafe.cmm.domain.entities.view

import br.com.usinasantafe.cmm.utils.Errors

data class ResultUpdate(
    val flagDialog: Boolean = false,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
    val failure: String = "",
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)