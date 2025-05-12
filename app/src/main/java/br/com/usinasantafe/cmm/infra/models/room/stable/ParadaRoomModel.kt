package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Parada // Import da entidade de domínio Parada
import br.com.usinasantafe.cmm.utils.TB_PARADA // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_PARADA) // Define como entidade do Room e especifica o nome da tabela
data class ParadaRoomModel (
    @PrimaryKey // Define idParada como chave primária
    val idParada: Int,
    val codParada: Int,
    val descrParada: String
)

// Função de extensão para converter ParadaRoomModel para a entidade de domínio Parada
fun ParadaRoomModel.roomModelToEntity(): Parada {
    return with(this){
        Parada(
            idParada = this.idParada,
            codParada = this.codParada,
            descrParada = this.descrParada
        )
    }
}

// Função de extensão para converter a entidade de domínio Parada para ParadaRoomModel
fun Parada.entityToRoomModel(): ParadaRoomModel {
    return with(this){
        ParadaRoomModel(
            idParada = this.idParada,
            codParada = this.codParada,
            descrParada = this.descrParada
        )
    }
}
