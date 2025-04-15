package br.com.usinasantafe.cmm.infra.models.retrofit.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Turno

data class TurnoRetrofitModel(
    val idTurno: Int,
    val codTurno: Int,
    val nroTurno: Int,
    val descrTurno: String,
)

fun TurnoRetrofitModel.retrofitModelToEntity(): Turno {
    return Turno(
        idTurno = this.idTurno,
        codTurno = this.codTurno,
        nroTurno = this.nroTurno,
        descrTurno = this.descrTurno
    )
}
