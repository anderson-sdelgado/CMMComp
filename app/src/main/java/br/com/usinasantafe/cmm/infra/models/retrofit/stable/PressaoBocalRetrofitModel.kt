package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.PressaoBocal

data class PressaoBocalRetrofitModel(
    val idPressaoBocal: Int,
    val idBocal: Int,
    val valorPressao: Double,
    val valorVeloc: Int,
)

fun PressaoBocalRetrofitModel.retrofitModelToEntity(): PressaoBocal {
    return PressaoBocal(
        idPressaoBocal = this.idPressaoBocal,
        idBocal = this.idBocal,
        valorPressao = this.valorPressao,
        valorVeloc = this.valorVeloc
    )
}
