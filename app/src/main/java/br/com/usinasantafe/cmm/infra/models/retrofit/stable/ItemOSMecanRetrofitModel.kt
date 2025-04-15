package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMecan

data class ItemOSMecanRetrofitModel(
    val idItemOS: Int,
    val idOS: Int,
    val seqItemOS: Int,
    val idServItemOS: Int,
    val idCompItemOS: Int
)

fun ItemOSMecanRetrofitModel.retrofitModelToEntity(): ItemOSMecan {
    return ItemOSMecan(
        idItemOS = this.idItemOS,
        idOS = this.idOS,
        seqItemOS = this.seqItemOS,
        idServItemOS = this.idServItemOS,
        idCompItemOS = this.idCompItemOS
    )
}
