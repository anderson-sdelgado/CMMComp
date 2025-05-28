package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipActivity

data class REquipActivityRetrofitModel(
    val idREquipActivity: Int,
    val idEquip: Int,
    val idActivity: Int
)

fun REquipActivityRetrofitModel.retrofitModelToEntity(): REquipActivity {
    return REquipActivity(
        idREquipActivity = this.idREquipActivity,
        idEquip = this.idEquip,
        idActivity = this.idActivity
    )
}
