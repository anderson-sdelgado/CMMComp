package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.RAtivParada // Import da entidade de domínio RAtivParada
import br.com.usinasantafe.cmm.utils.TB_R_ATIV_PARADA // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_R_ATIV_PARADA) // Define como entidade do Room e especifica o nome da tabela
data class RAtivParadaRoomModel (
    @PrimaryKey // Define idRAtivParada como chave primária
    val idRAtivParada: Int,
    val idAtiv: Int, // Chave estrangeira para Atividade
    val idParada: Int // Chave estrangeira para Parada
)

// Função de extensão para converter RAtivParadaRoomModel para a entidade de domínio RAtivParada
fun RAtivParadaRoomModel.roomToEntity(): RAtivParada {
    return with(this){
        RAtivParada(
            idRAtivParada = this.idRAtivParada,
            idAtiv = this.idAtiv,
            idParada = this.idParada
        )
    }
}

// Função de extensão para converter a entidade de domínio RAtivParada para RAtivParadaRoomModel
fun RAtivParada.entityToRoomModel(): RAtivParadaRoomModel {
    return with(this){
        RAtivParadaRoomModel(
            idRAtivParada = this.idRAtivParada,
            idAtiv = this.idAtiv,
            idParada = this.idParada
        )
    }
}
