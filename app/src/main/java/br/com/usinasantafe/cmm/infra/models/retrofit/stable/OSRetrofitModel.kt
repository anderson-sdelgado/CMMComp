package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.OS

data class OSRetrofitModel(
    val idOS: Int,
    val nroOS: Int,
    val idLibOS: Int,
    val idPropAgr: Int,
    val areaOS: Double,
    val typeOS: Int,
    val idEquip: Int,
)

fun OSRetrofitModel.retrofitModelToEntity(): OS {
    return OS(
        idOS = this.idOS,
        nroOS = this.nroOS,
        idLibOS = this.idLibOS,
        idPropAgr = this.idPropAgr,
        areaOS = this.areaOS,
        typeOS = this.typeOS,
        idEquip = this.idEquip
    )
}
