package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Atividade
import br.com.usinasantafe.cmm.utils.TB_ATIVIDADE

@Entity(tableName = TB_ATIVIDADE)
data class AtividadeRoomModel (
    @PrimaryKey
    val idAtiv: Int,
    val codAtiv: Int,
    val descrAtiv: String
)

fun AtividadeRoomModel.roomToEntity(): Atividade {
    return with(this){
        Atividade(
            idAtiv = this.idAtiv,
            codAtiv = this.codAtiv,
            descrAtiv = this.descrAtiv,
        )
    }
}

fun Atividade.entityToRoomModel(): AtividadeRoomModel {
    return with(this){
        AtividadeRoomModel(
            idAtiv = this.idAtiv,
            codAtiv = this.codAtiv,
            descrAtiv = this.descrAtiv,
        )
    }
}