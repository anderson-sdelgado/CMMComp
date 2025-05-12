package br.com.usinasantafe.cmm.infra.models.retrofit.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config


data class ConfigRetrofitModelOutput(
    var number: Long,
    val nroEquip: Long,
    var version: String,
    var app: String,
)

data class ConfigRetrofitModelInput(
    val idBD: Int,
    val idEquip: Int
)

fun Config.entityToRetrofitModel(): ConfigRetrofitModelOutput {

    require(number != 0L) { "The field 'number' cannot is null." }
    require(nroEquip != 0L) { "The field 'nroEquip' cannot is null." }

    return ConfigRetrofitModelOutput(
            number = number!!,
            version = version!!,
            app = app!!,
            nroEquip = nroEquip!!
        )
}

fun ConfigRetrofitModelInput.retrofitToEntity(): Config {

    require(idBD != 0) { "The field 'idBD' cannot is null." }
    require(idEquip != 0) { "The field 'idEquip' cannot is null." }

    return Config(
            idBD = idBD,
            idEquip = idEquip
        )
}