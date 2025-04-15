package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RAtivParada

data class RAtivParadaRetrofitModel(
    val idRAtivParada: Int,
    val idAtiv: Int,
    val idParada: Int,
)

fun RAtivParadaRetrofitModel.retrofitModelToEntity(): RAtivParada {
    return RAtivParada(
        idRAtivParada = this.idRAtivParada,
        idAtiv = this.idAtiv,
        idParada = this.idParada
    )
}
