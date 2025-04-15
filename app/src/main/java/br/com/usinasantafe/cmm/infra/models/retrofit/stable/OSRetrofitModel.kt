package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.OS

data class OSRetrofitModel(
    val idOS: Int,
    val nroOS: Int,
    val idLibOS: Int,
    val idProprAgr: Int,
    val areaProgrOS: Double,
    val tipoOS: Int,
    val idEquip: Int,
)

fun OSRetrofitModel.retrofitModelToEntity(): OS {
    return OS(
        idOS = this.idOS,
        nroOS = this.nroOS,
        idLibOS = this.idLibOS,
        idProprAgr = this.idProprAgr,
        areaProgrOS = this.areaProgrOS,
        tipoOS = this.tipoOS,
        idEquip = this.idEquip
    )
}
