package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Turno // Import da entidade de domínio Turno
import br.com.usinasantafe.cmm.utils.TB_TURNO // Import da constante do nome da tabela (precisa ser criada)

@Entity(tableName = TB_TURNO) // Define como entidade do Room e especifica o nome da tabela
data class TurnoRoomModel (
    @PrimaryKey // Define idTurno como chave primária (sem autoGenerate, assumindo que vem de fonte externa)
    val idTurno: Int,
    val codTurno: Int,
    val nroTurno: Int,
    val descrTurno: String,
)

// Função de extensão para converter TurnoRoomModel para a entidade de domínio Turno
fun TurnoRoomModel.roomToEntity(): Turno {
    return with(this){
        Turno(
            idTurno = this.idTurno,
            codTurno = this.codTurno,
            nroTurno = this.nroTurno,
            descrTurno = this.descrTurno
        )
    }
}

// Função de extensão para converter a entidade de domínio Turno para TurnoRoomModel
fun Turno.entityToRoomModel(): TurnoRoomModel {
    return with(this){
        TurnoRoomModel(
            idTurno = this.idTurno,
            codTurno = this.codTurno,
            nroTurno = this.nroTurno,
            descrTurno = this.descrTurno
        )
    }
}
