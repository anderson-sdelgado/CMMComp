package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Componente

data class ComponenteRetrofitModel(
    val idComponente: Int,
    val codComponente: Int,
    val descrComponente: String
)

fun ComponenteRetrofitModel.retrofitModelToEntity(): Componente {
    return Componente(
        idComponente = this.idComponente,
        codComponente = this.codComponente,
        descrComponente = this.descrComponente
    )
}
