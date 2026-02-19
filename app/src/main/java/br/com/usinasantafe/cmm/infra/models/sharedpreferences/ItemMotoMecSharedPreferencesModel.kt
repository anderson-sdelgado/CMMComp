package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.ItemMotoMec

data class ItemMotoMecSharedPreferencesModel(
    var idStop: Int? = null,
    var nroEquipTranshipment: Long? = null,
    var statusCon: Boolean? = true,
    var idNozzle: Int? = null,
    var valuePressure: Double? = null,
    var speedPressure: Int? = null
)

fun ItemMotoMecSharedPreferencesModel.sharedPreferencesModelToEntity(): ItemMotoMec {
    return with(this) {
        ItemMotoMec(
            idStop = idStop,
            nroEquipTranshipment = nroEquipTranshipment,
            statusCon = statusCon,
            idNozzle = idNozzle,
            valuePressure = valuePressure,
            speedPressure = speedPressure
        )
    }
}

fun ItemMotoMec.entityToSharedPreferencesModel(): ItemMotoMecSharedPreferencesModel {
    return with(this) {
        ItemMotoMecSharedPreferencesModel(
            idStop = idStop,
            nroEquipTranshipment = nroEquipTranshipment,
            statusCon = statusCon!!,
            idNozzle = idNozzle,
            valuePressure = valuePressure,
            speedPressure = speedPressure
        )
    }
}