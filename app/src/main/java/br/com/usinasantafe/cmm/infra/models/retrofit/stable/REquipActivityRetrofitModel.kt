package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipActivity

data class REquipActivityRetrofitModel(
    val idEquip: Int,
    val idActivity: Int
)

fun REquipActivityRetrofitModel.retrofitModelToEntity(): REquipActivity {
    return REquipActivity(
        idEquip = this.idEquip,
        idActivity = this.idActivity
    )
}
