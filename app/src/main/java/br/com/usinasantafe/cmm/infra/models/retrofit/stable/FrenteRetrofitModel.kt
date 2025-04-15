package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Frente

data class FrenteRetrofitModel(
    val idFrente: Int,
    val codFrente: Int,
    val descrFrente: String
)

fun FrenteRetrofitModel.retrofitModelToEntity(): Frente {
    return Frente(
        idFrente = this.idFrente,
        codFrente = this.codFrente,
        descrFrente = this.descrFrente
    )
}
