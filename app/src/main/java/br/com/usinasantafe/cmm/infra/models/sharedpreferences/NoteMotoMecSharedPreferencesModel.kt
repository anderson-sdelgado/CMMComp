package br.com.usinasantafe.cmm.infra.models.sharedpreferences

import br.com.usinasantafe.cmm.domain.entities.variable.ItemMotoMec

data class NoteMotoMecSharedPreferencesModel(
    var nroOS: Int? = null,
    var idActivity: Int? = null,
    var idStop: Int? = null,
    var nroEquipTranshipment: Long? = null,
    var statusCon: Boolean? = null,
)

fun NoteMotoMecSharedPreferencesModel.sharedPreferencesModelToEntity(): ItemMotoMec {
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

fun ItemMotoMec.entityToSharedPreferencesModel(): NoteMotoMecSharedPreferencesModel {
    return with(this) {
        NoteMotoMecSharedPreferencesModel(
            nroOS = nroOS,
            idActivity = idActivity,
            idStop = idStop,
            nroEquipTranshipment = nroEquipTranshipment,
            statusCon = statusCon
        )
    }
}