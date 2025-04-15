package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Propriedade

data class PropriedadeRetrofitModel(
    val idPropriedade: Int,
    val codPropriedade: Int,
    val descrPropriedade: String,
)

fun PropriedadeRetrofitModel.retrofitModelToEntity(): Propriedade {
    return Propriedade(
        idPropriedade = this.idPropriedade,
        codPropriedade = this.codPropriedade,
        descrPropriedade = this.descrPropriedade
    )
}
