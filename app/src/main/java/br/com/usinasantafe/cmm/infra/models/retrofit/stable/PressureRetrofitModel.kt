package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Pressure

data class PressureRetrofitModel(
    val id: Int,
    val idNozzle: Int,
    val value: Double,
    val speed: Int,
)

fun PressureRetrofitModel.retrofitModelToEntity(): Pressure {
    return Pressure (
        id = this.id,
        idNozzle = this.idNozzle,
        value = this.value,
        speed = this.speed,
    )
}