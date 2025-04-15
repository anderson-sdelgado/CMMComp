package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Leira
import br.com.usinasantafe.cmm.utils.StatusLeira

data class LeiraRetrofitModel(
    val idLeira: Int,
    val codLeira: Int,
    val statusLeira: Int
)

fun LeiraRetrofitModel.retrofitModelToEntity(): Leira {
    return Leira(
        idLeira = this.idLeira,
        codLeira = this.codLeira,
        statusLeira = StatusLeira.entries[this.statusLeira]
    )
}
