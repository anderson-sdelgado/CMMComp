package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Parada

data class ParadaRetrofitModel(
    val idParada: Int,
    val codParada: Int,
    val descrParada: String,
)

fun ParadaRetrofitModel.retrofitModelToEntity(): Parada {
    return Parada(
        idParada = this.idParada,
        codParada = this.codParada,
        descrParada = this.descrParada
    )
}
