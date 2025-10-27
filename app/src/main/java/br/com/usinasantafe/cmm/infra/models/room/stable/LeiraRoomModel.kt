package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Leira // Import da entidade de domínio Leira
import br.com.usinasantafe.cmm.utils.StatusLeira // Import do tipo StatusLeira
import br.com.usinasantafe.cmm.utils.TB_LEIRA // Import da constante do nome da tabela

@Entity(tableName = TB_LEIRA)
data class LeiraRoomModel (
    @PrimaryKey
    val idLeira: Int,
    val codLeira: Int,
    val statusLeira: Int
)

fun LeiraRoomModel.roomModelToEntity(): Leira {
    return with(this){
        Leira(
            idLeira = this.idLeira,
            codLeira = this.codLeira,
            statusLeira = StatusLeira.values()[this.statusLeira]
        )
    }
}

fun Leira.entityLeiraToRoomModel(): LeiraRoomModel {
    return with(this){
        LeiraRoomModel(
            idLeira = this.idLeira,
            codLeira = this.codLeira,
            statusLeira = this.statusLeira.ordinal
        )
    }
}
