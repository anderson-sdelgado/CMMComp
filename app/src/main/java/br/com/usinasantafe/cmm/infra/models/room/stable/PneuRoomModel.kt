package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Pneu // Import da entidade de domínio Pneu
import br.com.usinasantafe.cmm.utils.TB_PNEU // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_PNEU) // Define como entidade do Room e especifica o nome da tabela
data class PneuRoomModel (
    @PrimaryKey // Define idPneu como chave primária
    val idPneu: Int,
    val nroPneu: Int
)

// Função de extensão para converter PneuRoomModel para a entidade de domínio Pneu
fun PneuRoomModel.roomModelToEntity(): Pneu {
    return with(this){
        Pneu(
            idPneu = this.idPneu,
            nroPneu = this.nroPneu
        )
    }
}

// Função de extensão para converter a entidade de domínio Pneu para PneuRoomModel
fun Pneu.entityToRoomModel(): PneuRoomModel {
    return with(this){
        PneuRoomModel(
            idPneu = this.idPneu,
            nroPneu = this.nroPneu
        )
    }
}
