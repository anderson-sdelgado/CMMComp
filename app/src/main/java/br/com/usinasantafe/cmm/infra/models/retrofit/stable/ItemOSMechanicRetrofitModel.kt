package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMechanic

data class ItemOSMechanicRetrofitModelOutput(
    val idEquip: Int,
    val nroOS: Int,
)

data class ItemOSMechanicRetrofitModelInput(
    val id: Int,
    val nroOS: Int,
    val seqItem: Int,
    val idServ: Int,
    val idComp: Int
)

fun ItemOSMechanicRetrofitModelInput.retrofitModelToEntity(): ItemOSMechanic {
    return with(this) {
        ItemOSMechanic(
            id = this.id,
            nroOS = this.nroOS,
            seqItem = this.seqItem,
            idServ = this.idServ,
            idComp = this.idComp
        )
    }
}
