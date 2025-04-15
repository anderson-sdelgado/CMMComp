package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Atividade

data class AtividadeRetrofitModel(
    val idAtiv: Int,
    val codAtiv: Int,
    val descrAtiv: String
)

fun AtividadeRetrofitModel.retrofitModelToEntity(): Atividade {
    return Atividade(
        idAtiv = this.idAtiv,
        codAtiv = this.codAtiv,
        descrAtiv = this.descrAtiv
    )
}
