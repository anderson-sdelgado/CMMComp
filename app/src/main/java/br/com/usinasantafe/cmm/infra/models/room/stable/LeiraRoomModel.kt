package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Leira // Import da entidade de domínio Leira
import br.com.usinasantafe.cmm.utils.StatusLeira // Import do tipo StatusLeira
import br.com.usinasantafe.cmm.utils.TB_LEIRA // Import da constante do nome da tabela

@Entity(tableName = TB_LEIRA) // Define como entidade do Room e especifica o nome da tabela
data class LeiraRoomModel (
    @PrimaryKey // Define idLeira como chave primária
    val idLeira: Int,
    val codLeira: Int,
    val statusLeira: Int // Armazena o status como Int (ex: ordinal ou código do enum)
)

// Função de extensão para converter LeiraRoomModel para a entidade de domínio Leira
fun LeiraRoomModel.roomModelToEntity(): Leira {
    return with(this){
        Leira(
            idLeira = this.idLeira,
            codLeira = this.codLeira,
            statusLeira = StatusLeira.values()[this.statusLeira]
        )
    }
}

// Função de extensão para converter a entidade de domínio Leira para LeiraRoomModel
fun Leira.entityToRoomModel(): LeiraRoomModel {
    return with(this){
        LeiraRoomModel(
            idLeira = this.idLeira,
            codLeira = this.codLeira,
            statusLeira = this.statusLeira.ordinal
        )
    }
}
