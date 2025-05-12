package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Frente // Import da entidade de domínio Frente
import br.com.usinasantafe.cmm.utils.TB_FRENTE // Import da constante do nome da tabela

@Entity(tableName = TB_FRENTE) // Define como entidade do Room e especifica o nome da tabela
data class FrenteRoomModel (
    @PrimaryKey // Define idFrente como chave primária
    val idFrente: Int, // ID da Frente (ajustado para Int, conforme suposição)
    val codFrente: Int, // Código da Frente (ajustado para Int)
    val descrFrente: String // Descrição da Frente
)

// Função de extensão para converter FrenteRoomModel para a entidade de domínio Frente
fun FrenteRoomModel.roomModelToEntity(): Frente {
    return with(this){
        Frente(
            idFrente = this.idFrente,
            codFrente = this.codFrente,
            descrFrente = this.descrFrente
        )
    }
}

// Função de extensão para converter a entidade de domínio Frente para FrenteRoomModel
fun Frente.entityToRoomModel(): FrenteRoomModel {
    return with(this){
        FrenteRoomModel(
            idFrente = this.idFrente,
            codFrente = this.codFrente,
            descrFrente = this.descrFrente
        )
    }
}
