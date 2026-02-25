package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Component

data class ComponentRetrofitModel(
    val id: Int,
    val cod: Int,
    val descr: String
)

fun ComponentRetrofitModel.retrofitModelToEntity(): Component {
    return Component(
        id = this.id,
        cod = this.cod,
        descr = this.descr
    )
}
