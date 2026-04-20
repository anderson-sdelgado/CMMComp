package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.OS

data class OSRetrofitModel(
    val idOS: Int,
    val nroOS: Int,
    val idReleaseOS: Int? = null,
    val idPropAgr: Int? = null,
    val areaOS: Double? = null,
)

fun OSRetrofitModel.retrofitModelToEntity(): OS {
    return OS(
        idOS = this.idOS,
        nroOS = this.nroOS,
        idReleaseOS = this.idReleaseOS,
        idPropAgr = this.idPropAgr,
        areaOS = this.areaOS,
    )
}
