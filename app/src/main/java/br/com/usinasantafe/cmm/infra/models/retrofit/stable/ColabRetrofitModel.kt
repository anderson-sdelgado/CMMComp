package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Colab

data class ColabRetrofitModel(
    val matricColab: Long,
    val nomeColab: String
)

fun ColabRetrofitModel.retrofitModelToEntity(): Colab {
    return Colab(
        matricColab = this.matricColab,
        nomeColab = this.nomeColab
    )
}
