package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Colab // Supondo que a entidade de domínio Colab exista neste pacote
import br.com.usinasantafe.cmm.lib.TB_COLAB // Supondo que a constante para o nome da tabela exista

@Entity(tableName = TB_COLAB)
data class ColabRoomModel (
    @PrimaryKey
    val regColab: Long,
    val nameColab: String
)

fun ColabRoomModel.roomModelToEntity(): Colab {
    return with(this){
        Colab(
            regColab = this.regColab,
            nameColab = this.nameColab,
        )
    }
}

fun Colab.entityColabToRoomModel(): ColabRoomModel {
    return with(this){
        ColabRoomModel(
            regColab = this.regColab,
            nameColab = this.nameColab,
        )
    }
}
