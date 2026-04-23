package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.OS

data class OSRetrofitModel(
    val id: Int,
    val nro: Int,
    val idRelease: Int? = null,
    val idPropAgr: Int? = null,
    val area: Double? = null,
)

fun OSRetrofitModel.retrofitModelToEntity(): OS {
    return OS(
        id = this.id,
        nro = this.nro,
        idRelease = this.idRelease,
        idPropAgr = this.idPropAgr,
        area = this.area,
    )
}
