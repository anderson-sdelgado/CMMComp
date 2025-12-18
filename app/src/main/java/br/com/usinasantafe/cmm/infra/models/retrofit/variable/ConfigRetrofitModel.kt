package br.com.usinasantafe.cmm.infra.models.retrofit.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.EquipMainRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity


data class ConfigRetrofitModelOutput(
    var number: Long,
    val nroEquip: Long,
    var version: String,
    var app: String,
)

data class ConfigRetrofitModelInput(
    val idServ: Int,
    val equip: EquipMainRetrofitModel
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

    require(idServ != 0) { "The field 'idServ' cannot is null." }

    return Config(
            idServ = idServ,
            equip = equip.retrofitModelToEntity(),
        )
}