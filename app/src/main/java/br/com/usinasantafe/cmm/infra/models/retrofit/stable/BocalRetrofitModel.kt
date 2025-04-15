package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Bocal

data class BocalRetrofitModel(
    val idBocal: Int,
    val codBocal: Int,
    val descrBocal: String
)

fun BocalRetrofitModel.retrofitModelToEntity(): Bocal {
    return Bocal(
        idBocal = this.idBocal,
        codBocal = this.codBocal,
        descrBocal = this.descrBocal
    )
}
