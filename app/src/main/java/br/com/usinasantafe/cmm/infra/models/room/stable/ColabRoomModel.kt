package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Colab
import br.com.usinasantafe.cmm.utils.TB_COLAB

@Entity(tableName = TB_COLAB)
data class ColabRoomModel (
    @PrimaryKey
    val matricColab: Long,
    val nomeColab: String,
)

fun ColabRoomModel.toColab(): Colab {
    return with(this){
        Colab(
            matricColab = this.matricColab,
            nomeColab = this.nomeColab,
        )
    }
}

fun Colab.toColabModel(): ColabRoomModel {
    return with(this){
        ColabRoomModel(
            matricColab = this.matricColab,
            nomeColab = this.nomeColab,
        )
    }
}