package br.com.usinasantafe.cmm.infra.models.retrofit.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config


data class ConfigRetrofitModelOutput(
    var number: Long,
    val nroEquip: Long,
    var version: String,
    var app: String,
)

data class ConfigRetrofitModelInput(
    var idBD: Int,
)

fun Config.entityToRetrofitModel(): ConfigRetrofitModelOutput {
    return with(this){
        ConfigRetrofitModelOutput(
            number = this.number!!,
            version = this.version!!,
            app = this.app!!,
            nroEquip = this.nroEquip!!
        )
    }
}

fun ConfigRetrofitModelInput.retrofitToEntity(): Config {
    return with(this){
        Config(
            idBD = this.idBD
        )
    }
}