package br.com.usinasantafe.cmm.presenter.configuration.config

import br.com.usinasantafe.cmm.domain.entities.variable.Config

data class ConfigModel(
    val number: String,
    val nroEquip: String,
    val password: String,
    val checkMotoMec: Boolean,
)

fun Config.toConfigModel(): ConfigModel {
    return with(this){
        ConfigModel(
            number = this.number!!.toString(),
            nroEquip = this.nroEquip!!.toString(),
            password = this.password!!,
            checkMotoMec = this.checkMotoMec!!
        )
    }
}
