package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Colab // Supondo que a entidade de domínio Colab exista neste pacote
import br.com.usinasantafe.cmm.utils.TB_COLAB // Supondo que a constante para o nome da tabela exista

@Entity(tableName = TB_COLAB) // Define como entidade do Room e especifica o nome da tabela
data class ColabRoomModel (
    @PrimaryKey
    val matricColab: Long, // Matrícula do Colaborador
    val nomeColab: String // Nome do Colaborador
)

// Função de extensão para converter ColabRoomModel para a entidade de domínio Colab
fun ColabRoomModel.roomToEntity(): Colab {
    return with(this){
        Colab(
            matricColab = this.matricColab,
            nomeColab = this.nomeColab,
        )
    }
}

// Função de extensão para converter a entidade de domínio Colab para ColabRoomModel
fun Colab.entityToRoomModel(): ColabRoomModel {
    return with(this){
        ColabRoomModel(
            matricColab = this.matricColab,
            nomeColab = this.nomeColab,
        )
    }
}
