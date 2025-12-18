package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.ItemMotoMec

data class ItemMotoMecSharedPreferencesModel(
    var nroOS: Int? = null,
    var idActivity: Int? = null,
    var idStop: Int? = null,
    var nroEquipTranshipment: Long? = null,
    var statusCon: Boolean? = null,
)

fun ItemMotoMecSharedPreferencesModel.sharedPreferencesModelToEntity(): ItemMotoMec {
    return with(this) {
        ItemMotoMec(
            nroOS = nroOS,
            idActivity = idActivity,
            idStop = idStop,
            nroEquipTranshipment = nroEquipTranshipment,
            statusCon = statusCon
        )
    }
}

fun ItemMotoMec.entityToSharedPreferencesModel(): ItemMotoMecSharedPreferencesModel {
    return with(this) {
        ItemMotoMecSharedPreferencesModel(
            nroOS = nroOS,
            idActivity = idActivity,
            idStop = idStop,
            nroEquipTranshipment = nroEquipTranshipment,
            statusCon = statusCon
        )
    }
}