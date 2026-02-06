package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Nozzle

data class BocalRetrofitModel(
    val idBocal: Int,
    val codBocal: Int,
    val descrBocal: String
)

fun BocalRetrofitModel.retrofitModelToEntity(): Nozzle {
    return Nozzle(
        idBocal = this.idBocal,
        codBocal = this.codBocal,
        descrBocal = this.descrBocal
    )
}
