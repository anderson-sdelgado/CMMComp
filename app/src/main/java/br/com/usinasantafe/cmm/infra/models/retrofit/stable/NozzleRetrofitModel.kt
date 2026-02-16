package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Nozzle

data class NozzleRetrofitModel(
    val id: Int,
    val cod: Int,
    val descr: String,
)

fun NozzleRetrofitModel.retrofitModelToEntity(): Nozzle {
    return with(this) {
        Nozzle(
            id = this.id,
            cod = this.cod,
            descr = this.descr
        )
    }
}

