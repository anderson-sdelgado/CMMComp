package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Servico

data class ServicoRetrofitModel(
    val idServico: Int,
    val codServico: Int,
    val descrServico: String,
)

fun ServicoRetrofitModel.retrofitModelToEntity(): Servico {
    return Servico(
        idServico = this.idServico,
        codServico = this.codServico,
        descrServico = this.descrServico
    )
}
