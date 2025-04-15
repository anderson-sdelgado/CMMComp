package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipAtiv

data class REquipAtivRetrofitModel(
    val idREquipAtiv: Int,
    val idEquip: Int,
    val idAtiv: Int
)

fun REquipAtivRetrofitModel.retrofitModelToEntity(): REquipAtiv {
    return REquipAtiv(
        idREquipAtiv = this.idREquipAtiv,
        idEquip = this.idEquip,
        idAtiv = this.idAtiv
    )
}
