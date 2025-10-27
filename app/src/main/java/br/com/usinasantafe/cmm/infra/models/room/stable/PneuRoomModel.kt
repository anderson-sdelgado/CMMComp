package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Pneu // Import da entidade de domínio Pneu
import br.com.usinasantafe.cmm.utils.TB_PNEU // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_PNEU)
data class PneuRoomModel (
    @PrimaryKey
    val idPneu: Int,
    val nroPneu: Int
)

fun PneuRoomModel.roomModelToEntity(): Pneu {
    return with(this){
        Pneu(
            idPneu = this.idPneu,
            nroPneu = this.nroPneu
        )
    }
}

fun Pneu.entityPneuToRoomModel(): PneuRoomModel {
    return with(this){
        PneuRoomModel(
            idPneu = this.idPneu,
            nroPneu = this.nroPneu
        )
    }
}
