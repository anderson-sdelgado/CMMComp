package br.com.usinasantafe.cmm.presenter.model

import br.com.usinasantafe.cmm.domain.entities.variable.Config

data class ConfigModel(
    val number: String,
    val nroEquip: String,
    val password: String,
    val checkMotoMec: Boolean,
)

fun Config.toConfigModel(): ConfigModel {
    fun <T> T?.required(field: String): T =
        this ?: throw NullPointerException("$field is required")
    return with(this){
        ConfigModel(
            number = this.number.required("number").toString(),
            nroEquip = this.nroEquip.required("nroEquip").toString(),
            password = this.password.required("password"),
            checkMotoMec = this.checkMotoMec.required("checkMotoMec")
        )
    }
}
