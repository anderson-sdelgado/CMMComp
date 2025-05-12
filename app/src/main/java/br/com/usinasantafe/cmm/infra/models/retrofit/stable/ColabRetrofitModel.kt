package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Colab

data class ColabRetrofitModel(
    val regColab: Long,
    val nameColab: String
)

fun ColabRetrofitModel.retrofitModelToEntity(): Colab {
    return Colab(
        regColab = this.regColab,
        nameColab = this.nameColab
    )
}
