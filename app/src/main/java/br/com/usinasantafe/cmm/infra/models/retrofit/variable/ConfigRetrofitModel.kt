package br.com.usinasantafe.cmm.infra.models.retrofit.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.EquipMainRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.utils.required


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
    return ConfigRetrofitModelOutput(
            number = ::number.required(),
            version = version!!,
            app = app!!,
            nroEquip = ::nroEquip.required()
        )
}

fun ConfigRetrofitModelInput.retrofitToEntity(): Config {

    return Config(
            idServ = ::idServ.required(),
            equip = equip.retrofitModelToEntity(),
        )
}