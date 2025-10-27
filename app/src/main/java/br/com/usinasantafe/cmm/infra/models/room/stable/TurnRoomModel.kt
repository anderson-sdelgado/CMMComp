package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Turn // Import da entidade de domínio Turno
import br.com.usinasantafe.cmm.utils.TB_TURN // Import da constante do nome da tabela (precisa ser criada)

@Entity(tableName = TB_TURN)
data class TurnRoomModel (
    @PrimaryKey
    val idTurn: Int,
    val codTurnEquip: Int,
    val nroTurn: Int,
    val descrTurn: String,
)

fun TurnRoomModel.roomModelToEntity(): Turn {
    return with(this){
        Turn(
            idTurn = this.idTurn,
            codTurnEquip = this.codTurnEquip,
            nroTurn = this.nroTurn,
            descrTurn = this.descrTurn
        )
    }
}

fun Turn.entityTurnToRoomModel(): TurnRoomModel {
    return with(this){
        TurnRoomModel(
            idTurn = this.idTurn,
            codTurnEquip = this.codTurnEquip,
            nroTurn = this.nroTurn,
            descrTurn = this.descrTurn
        )
    }
}
